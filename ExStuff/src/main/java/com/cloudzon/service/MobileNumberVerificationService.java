package com.cloudzon.service;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudzon.config.PlivoConfiguration;
import com.cloudzon.errors.SMSCustomException;
import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.call.Call;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;

@Service
public class MobileNumberVerificationService {

	private static final Logger logger = LoggerFactory.getLogger(MobileNumberVerificationService.class);

	@Autowired
	private PlivoConfiguration plivoConfiguration;

	public String sendMobileVerificationSMS(String mobileNumber, String token) {
		logger.info("sendMobileVerificationSMS");

		String auth_id = plivoConfiguration.getAUTH_ID();
		String auth_token = plivoConfiguration.getAUTH_TOKEN();

		try {
			RestAPI api = new RestAPI(auth_id, auth_token, "v1");

			// Build a filter for the MessageList
			String strMessage = "Welcome to Ex Stuff. \nYour 6-digit code is " + token;

			LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
			parameters.put("dst", mobileNumber);
			parameters.put("src", plivoConfiguration.getACCOUNT_MOBILENUMBER());
			parameters.put("text", strMessage);
			MessageResponse msgResponse = api.sendMessage(parameters);

			if (msgResponse.serverCode == 202) {
				return msgResponse.messageUuids.get(0).toString();
			} else {
				throw new SMSCustomException("40203", msgResponse.error, "SMS Exception", Boolean.TRUE);
			}

		} catch (PlivoException e) {
			throw new SMSCustomException("40203", e.getLocalizedMessage(), "SMS Exception", Boolean.TRUE);
		}
	}

	public Boolean sendMobileVerificationCall(String mobileNumber, String responseUrl) {
		logger.info("sendMobileVerificationCall");
		String auth_id = plivoConfiguration.getAUTH_ID();
		String auth_token = plivoConfiguration.getAUTH_TOKEN();
		try {
			RestAPI api = new RestAPI(auth_id, auth_token, "v1");
			LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
			parameters.put("to", mobileNumber);
			parameters.put("from", plivoConfiguration.getACCOUNT_MOBILENUMBER());
			parameters.put("answer_url", responseUrl);
			parameters.put("answer_method", "GET");
			// Make an outbound call and print the response
			Call resp = api.makeCall(parameters);
			if (resp != null) {
				return Boolean.TRUE;
			} else {
				throw new SMSCustomException("40203", "Something went wrong in making call.", "SMS Exception",
						Boolean.TRUE);
			}
		} catch (PlivoException e) {
			throw new SMSCustomException("40203", e.getLocalizedMessage(), "SMS Exception", Boolean.TRUE);
		}
	}

}
