package com.cloudzon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class PlivoConfiguration {

	public static String AUTH_ID;

	public static String AUTH_TOKEN;

	public static String ACCOUNT_MOBILENUMBER;

	public static String CALL_BASEURL;

	public PlivoConfiguration() {
		super();
	}

	public String getAUTH_ID() {
		return AUTH_ID;
	}

	@Value("${plivo.auth.id}")
	public void setAUTH_ID(String aUTH_ID) {
		PlivoConfiguration.AUTH_ID = aUTH_ID;
	}

	public String getAUTH_TOKEN() {
		return AUTH_TOKEN;
	}

	@Value("${plivo.auth.token}")
	public void setAUTH_TOKEN(String aUTH_TOKEN) {
		PlivoConfiguration.AUTH_TOKEN = aUTH_TOKEN;
	}

	public String getACCOUNT_MOBILENUMBER() {
		return ACCOUNT_MOBILENUMBER;
	}

	@Value("${plivo.account.mobileNumber}")
	public void setACCOUNT_MOBILENUMBER(String aCCOUNT_MOBILENUMBER) {
		PlivoConfiguration.ACCOUNT_MOBILENUMBER = aCCOUNT_MOBILENUMBER;
	}

	public String getCALL_BASEURL() {
		return CALL_BASEURL;
	}

	@Value("${plivo.call.baseurl}")
	public void setCALL_BASEURL(String cALL_BASEURL) {
		PlivoConfiguration.CALL_BASEURL = cALL_BASEURL;
	}

}
