package com.cloudzon.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.plivo.helper.exception.PlivoException;
import com.plivo.helper.xml.elements.PlivoResponse;
import com.plivo.helper.xml.elements.Speak;

@Controller
public class PlivoController {

	private static final Logger logger = LoggerFactory.getLogger(PlivoController.class);

	@RequestMapping(value = "plivoCall.xml", produces = "application/xml")
	public void generateXmlForCallVerification(HttpServletRequest request, HttpServletResponse resp) {
		logger.info("generateXmlForCallVerification");
		PlivoResponse response = new PlivoResponse();
		String token;
		try {
			token = request.getParameter("token");
			token = token.replaceAll(".(?!$)", "$0    						");
			final String SAY_MESSAGE = "Your Ex Stuff account verification code is        " + token
					+ " . I repeat  Your Ex Stuff account verification code is     " + token + " .";
			Speak spk1 = new Speak(SAY_MESSAGE);
			spk1.setLanguage("en-US");
			spk1.setVoice("MAN");
			response.append(spk1);
			final HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_XML);
			System.out.println(response);
			resp.addHeader("Content-Type", "text/xml");
			resp.getWriter().print(response.toXML());
		} catch (PlivoException e) {
			logger.error("generateXmlForCallVerification: " + e.getMessage());
		} catch (IOException e) {
			logger.error("generateXmlForCallVerification: " + e.getMessage());
		}
	}

}
