package com.tang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class TestController {

	@RequestMapping("/abc")
	public String index() {
		System.out.println("index1...");
		return "index";
	}
}
