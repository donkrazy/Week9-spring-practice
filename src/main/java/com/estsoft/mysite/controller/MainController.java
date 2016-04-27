package com.estsoft.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping( "/" )
	public String root() {
		System.out.println(123123);
		return "/main/index";
	}
	@RequestMapping( "/main" )
	public String index() {
		return "/main/index";
	}
}