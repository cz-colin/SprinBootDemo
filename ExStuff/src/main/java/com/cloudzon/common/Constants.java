package com.cloudzon.common;

import java.math.BigDecimal;

public interface Constants {

	String DATE_FORMAT_WITH_TIME = "dd/MM/yyyy HH:mm:ss.SSS";

	// common
	String REGION = "US";
	String SESSION_USER = "sessionUser";
	String SESSION_ADMIN = "sessionAdmin";
	String SESSION_ENTERPRISE = "sessionSuperAdmin";
	String UTF8 = "UTF-8";
	String STRING_EMPTY = "";
	BigDecimal PERCENTAGE = new BigDecimal(100);
	Double RANGE_IN_MILE = 1.24;
	Integer SCALE = 3;
	String COMMA = ",";
	String STRING_SLASH = "/";
	BigDecimal CENT = new BigDecimal(100);

	Integer REGISTER_EMAIL_LINK_EXPIRE_TIME = 12 * 24;
	Integer REGISTER_MESSAGE_LINK_EXPIRE_TIME = 12 * 24;
	Integer LOST_PASSWORD_EMAIL_LINK_EXPIRE_TIME = 12 * 24;
	Integer ACTIVE_USER_EMAIL_LINK_EXPIRE_TIME = 12 * 24;

	public static final String SYSTEM_ACCOUNT = "system";
	public static final String ANONYMOUS_USER = "anonymoususer";

	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
	public static final String SPRING_PROFILE_TEST = "test";
	public static final String SPRING_PROFILE_PRODUCTION = "prod";
	public static final String SPRING_PROFILE_SWAGGER = "swagger";

	// Default ROLE
	String DEFAULT_ROLE = "ROLE_USER";
}
