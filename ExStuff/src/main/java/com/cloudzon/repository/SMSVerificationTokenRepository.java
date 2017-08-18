package com.cloudzon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cloudzon.domain.SMSVerificationToken;
import com.cloudzon.domain.SMSVerificationToken.VerificationTokenType;
import com.cloudzon.domain.User;

@Repository("smsVerificationTokenRepository")
public interface SMSVerificationTokenRepository extends JpaRepository<SMSVerificationToken, Long> {

	@Query(value = "SELECT smsVerificationToken FROM SMSVerificationToken AS smsVerificationToken WHERE smsVerificationToken.tokenType=:tokenType AND smsVerificationToken.token=:token AND smsVerificationToken.user =:user")
	public SMSVerificationToken getVerifactionTokenByToken(@Param("token") String token,
			@Param("tokenType") VerificationTokenType tokenType, @Param("user") User user);

}
