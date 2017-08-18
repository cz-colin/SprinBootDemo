package com.cloudzon.service;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudzon.common.Constants;
import com.cloudzon.domain.Authority;
import com.cloudzon.domain.SMSVerificationToken;
import com.cloudzon.domain.User;
import com.cloudzon.dto.LoginDTO;
import com.cloudzon.dto.UserLoginDto;
import com.cloudzon.errors.AlreadyVerifiedException;
import com.cloudzon.errors.AlreadyVerifiedException.AlreadyVerifiedExceptionType;
import com.cloudzon.errors.NotFoundException;
import com.cloudzon.errors.NotFoundException.NotFound;
import com.cloudzon.repository.AuthorityRepository;
import com.cloudzon.repository.SMSVerificationTokenRepository;
import com.cloudzon.repository.UserRepository;
import com.cloudzon.util.DateUtil;

@Service("userService")
@Transactional
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Resource
	private MobileNumberVerificationService mobileNumberVerificationService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private SMSVerificationTokenRepository smsVerificationTokenRepository;

	@Transactional
	public User login(UserLoginDto loginDto) {
		logger.info("login start");
		User objUser = null;
		try {
			objUser = this.userRepository.findOneByMobileNumber(loginDto.getMobileNumber());
			if (objUser != null) {

				SMSVerificationToken objSmsVerificationToken = new SMSVerificationToken(objUser,
						SMSVerificationToken.VerificationTokenType.loginVerification,
						Constants.REGISTER_MESSAGE_LINK_EXPIRE_TIME);

				// String messageSID = "a1234";
				String messageSID = this.mobileNumberVerificationService
						.sendMobileVerificationSMS(loginDto.getMobileNumber(), objSmsVerificationToken.getToken());
				objSmsVerificationToken.setMessageSID(messageSID);
				objSmsVerificationToken.setCreatedBy(objUser.getId());
				objSmsVerificationToken.setMobileNumber(loginDto.getMobileNumber());
				objSmsVerificationToken.setIsUsed(false);
				objSmsVerificationToken.setUser(objUser);
				this.smsVerificationTokenRepository.save(objSmsVerificationToken);

			} else {
				Set<Authority> userAuthorityList = new HashSet<Authority>();
				objUser = new User();
				objUser.setIsActive(Boolean.TRUE);
				objUser.setIsVerified(Boolean.FALSE);
				objUser.setIsSuspect(Boolean.FALSE);
				objUser.setProfilePicture("default.png");
				objUser.setMobileNumber(loginDto.getMobileNumber());
				objUser.setUsername(loginDto.getMobileNumber());

				Authority objAuthority = this.authorityRepository.findOne(Constants.DEFAULT_ROLE);
				userAuthorityList.add(objAuthority);
				objUser.setAuthorities(userAuthorityList);
				this.userRepository.save(objUser);

				SMSVerificationToken objSmsVerificationToken = new SMSVerificationToken(objUser,
						SMSVerificationToken.VerificationTokenType.loginVerification,
						Constants.REGISTER_MESSAGE_LINK_EXPIRE_TIME);

				// String messageSID = "a1234";
				String messageSID = this.mobileNumberVerificationService
						.sendMobileVerificationSMS(loginDto.getMobileNumber(), objSmsVerificationToken.getToken());

				objSmsVerificationToken.setMessageSID(messageSID);
				objSmsVerificationToken.setCreatedBy(objUser.getId());
				objSmsVerificationToken.setMobileNumber(loginDto.getMobileNumber());
				objSmsVerificationToken.setIsUsed(false);
				objSmsVerificationToken.setUser(objUser);
				this.smsVerificationTokenRepository.save(objSmsVerificationToken);
			}
		} finally {
			logger.info("login end");
		}
		return objUser;
	}

	@Transactional
	public User verificationCall(UserLoginDto loginDto, String responseUrl) throws UnsupportedEncodingException {
		logger.info("verificationCall start");
		User objUser = null;
		objUser = this.userRepository.findOneByMobileNumber(loginDto.getMobileNumber());
		SMSVerificationToken objSmsVerificationToken = new SMSVerificationToken(objUser,
				SMSVerificationToken.VerificationTokenType.loginVerification,
				Constants.REGISTER_MESSAGE_LINK_EXPIRE_TIME);
		responseUrl = responseUrl + "?token=" + objSmsVerificationToken.getToken();
		this.mobileNumberVerificationService.sendMobileVerificationCall(loginDto.getMobileNumber(), responseUrl);

		objSmsVerificationToken.setCreatedBy(objUser.getId());
		objSmsVerificationToken.setMobileNumber(loginDto.getMobileNumber());
		objSmsVerificationToken.setIsUsed(false);
		objSmsVerificationToken.setUser(objUser);
		objSmsVerificationToken.setCreatedDate(DateUtil.getCurrentDate());
		this.smsVerificationTokenRepository.save(objSmsVerificationToken);
		logger.info("verificationCall end");
		return objUser;
	}

	@Transactional
	public User verifyLogin(LoginDTO loginDTO) {
		User objUser = null;
		objUser = this.userRepository.findByUsername(loginDTO.getUsername());
		// update user verified
		objUser.setIsVerified(Boolean.TRUE);
		this.userRepository.save(objUser);

		SMSVerificationToken objSmsVerificationToken = this.smsVerificationTokenRepository.getVerifactionTokenByToken(
				loginDTO.getVerificationCode(), SMSVerificationToken.VerificationTokenType.loginVerification, objUser);
		if (objSmsVerificationToken != null) {
			if (objSmsVerificationToken.getIsUsed()) {
				throw new AlreadyVerifiedException(AlreadyVerifiedExceptionType.Token);
			} else { // update used token //
				objSmsVerificationToken.setIsUsed(true);
				this.smsVerificationTokenRepository.save(objSmsVerificationToken);
				// update user verified
				objUser = objSmsVerificationToken.getUser();
				objUser.setIsVerified(Boolean.TRUE);
				this.userRepository.save(objUser);
				return objUser;
			}
		} else {
			throw new NotFoundException(NotFound.VerificationCodeNotFound, Boolean.TRUE);
		}

	}
}
