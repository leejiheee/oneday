package com.oneday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClassController {
	
	@GetMapping(value="/oneday/list")
	public String classList() {
		return "oneday/classList";
	}
}
