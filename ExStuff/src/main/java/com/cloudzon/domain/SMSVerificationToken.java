package com.cloudzon.domain;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.cloudzon.common.Constants;

/**
 * A token that gives the user permission to carry out a specific task once
 * within a determined time period. An example would be a Lost Password token.
 * The user receives the token embedded in a link. They send the token back to
 * the server by clicking the link and the action is processed
 * 
 */
@Entity
@Table(name = "es_sms_verification_token")
public class SMSVerificationToken extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24; // 24 hours

	@Id
	@Column(name = "sms_verification_tokenr_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "token", length = 36, nullable = false)
	private final String token;

	@Column(name = "expire_at", nullable = false)
	@DateTimeFormat(pattern = Constants.DATE_FORMAT_WITH_TIME)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "token_type", nullable = false)
	private VerificationTokenType tokenType;

	@Column(name = "is_used", nullable = false)
	private Boolean isUsed;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "mobile_number", length = 15)
	private String mobileNumber;

	@Column(name = "message_sid", length = 255)
	private String messageSID;

	public SMSVerificationToken() {
		super();
		this.token = String.valueOf(getRandomDigit());
		this.expireAt = calculateExpiryDate(DEFAULT_EXPIRY_TIME_IN_MINS);
	}

	public int getRandomDigit() {
		Random random = new Random();
		int min = 100000;
		int max = 999999;
		return random.nextInt(max - min) + min;
	}

	public SMSVerificationToken(User user, VerificationTokenType tokenType, int expirationTimeInMinutes) {
		this();
		this.user = user;
		this.tokenType = tokenType;
		this.expireAt = calculateExpiryDate(expirationTimeInMinutes);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}

	public VerificationTokenType getTokenType() {
		return tokenType;
	}

	public void setTokenType(VerificationTokenType tokenType) {
		this.tokenType = tokenType;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		DateTime now = new DateTime();
		return now.plusMinutes(expiryTimeInMinutes).toDate();
	}

	public enum VerificationTokenType {
		loginVerification
	}

	public boolean hasExpired() {
		DateTime tokenDate = new DateTime(getExpireAt());
		return tokenDate.isBeforeNow();
	}

	public String getMessageSID() {
		return messageSID;
	}

	public void setMessageSID(String messageSID) {
		this.messageSID = messageSID;
	}

}
