package com.cloudzon.controller;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudzon.config.PlivoConfiguration;
import com.cloudzon.domain.User;
import com.cloudzon.dto.LoginDTO;
import com.cloudzon.dto.LoginSignUpResponseDTO;
import com.cloudzon.dto.ResponseDTO;
import com.cloudzon.dto.UserLoginDto;
import com.cloudzon.security.security.jwt.JWTConfigurer;
import com.cloudzon.security.security.jwt.TokenProvider;
import com.cloudzon.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

	private final Logger log = LoggerFactory.getLogger(UserJWTController.class);

	private final TokenProvider tokenProvider;

	private final AuthenticationManager authenticationManager;

	@Autowired
	private PlivoConfiguration plivoConfiguration;

	public UserJWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@Resource(name = "userService")
	private UserService userService;

	@PostMapping("preAuthenticateSMS")
	public ResponseDTO preAuthorizeBySMS(@Valid @RequestBody UserLoginDto loginDto) {
		User user = this.userService.login(loginDto);
		return new ResponseDTO(new LoginSignUpResponseDTO(user.getUsername()), Boolean.FALSE);
	}

	@PostMapping("preAuthenticateCall")
	public ResponseDTO preAuthorizeByCall(@Valid @RequestBody UserLoginDto loginDto, HttpServletRequest request)
			throws UnsupportedEncodingException {
		StringBuilder responseUrl = new StringBuilder();
		responseUrl.append(plivoConfiguration.getCALL_BASEURL()).append(":").append(request.getServerPort())
				.append("/plivoCall.xml");
		User user = this.userService.verificationCall(loginDto, responseUrl.toString());
		return new ResponseDTO(new LoginSignUpResponseDTO(user.getUsername()), Boolean.FALSE);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authorize(@Valid @RequestBody LoginDTO login, HttpServletResponse response) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				login.getUsername(), "NoPassword");
		User user = this.userService.verifyLogin(login);

		try {
			if (user != null) {
				Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				boolean rememberMe = (login.getRememberMe() == null) ? false : login.getRememberMe();
				String jwt = tokenProvider.createToken(authentication, rememberMe);
				response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
				return ResponseEntity.ok(new JWTToken(jwt));
			} else {
				return new ResponseEntity<>(
						Collections.singletonMap("AuthenticationException", "Verification Code Not Matched."),
						HttpStatus.UNAUTHORIZED);
			}

		} catch (AuthenticationException ae) {
			log.trace("Authentication exception trace: {}", ae);
			return new ResponseEntity<>(Collections.singletonMap("AuthenticationException", ae.getLocalizedMessage()),
					HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private String idToken;

		JWTToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}
	}
}
