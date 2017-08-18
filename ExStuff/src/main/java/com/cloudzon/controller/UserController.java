package com.cloudzon.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudzon.dto.BroadcastMessageDTO;
import com.cloudzon.dto.ResponseDTO;
import com.cloudzon.dto.ResponseMessageDTO;
import com.cloudzon.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource(name = "userService")
	private UserService userService;

	@PostMapping(value = "createBroadcast", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseDTO createBroadcastMessage(
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "latitude", required = true) String latitude,
			@RequestParam(value = "longitude", required = true) String longitude,
			@RequestParam(value = "expiresInDay", required = true) Double expiresInDay, HttpServletRequest request)
			throws IOException, URISyntaxException {
		logger.info("createBroadcastMessage");

		BroadcastMessageDTO broadcastMessageDTO = new BroadcastMessageDTO();
		broadcastMessageDTO.setTitle(title);
		broadcastMessageDTO.setDescription(description);
		broadcastMessageDTO.setLatitude(latitude);
		broadcastMessageDTO.setLongitude(longitude);
		broadcastMessageDTO.setExpiresIn(expiresInDay);
		if (imageFile != null) {
			broadcastMessageDTO.setImage(imageFile);
		} else {
			broadcastMessageDTO.setImage(null);
		}
		/* =create service */
		return new ResponseDTO(new ResponseMessageDTO("Created successfully"), Boolean.TRUE);
	}
}
