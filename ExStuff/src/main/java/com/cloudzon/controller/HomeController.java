package com.cloudzon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/login")
	public String login() {
		return "index";
	}

	@GetMapping("/403")
	public String error403() {
		return "/error/403";
	}

}
