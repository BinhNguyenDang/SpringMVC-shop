package com.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotFoundController {

	@GetMapping(value = "/notFound")
	public String notFound(Model model) {

		return "site/notFound";
	}
}
