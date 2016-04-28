package com.estsoft.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVo;

@Controller
@RequestMapping( "/user" )
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( "/joinform" )
	public String joinform() {
		return "/user/joinform";
	}

	@RequestMapping( value="/join", method=RequestMethod.POST )
	public String join( @ModelAttribute UserVo vo ) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping( "/joinsuccess" )
	public String joinSuccess() {
		return "/user/joinsuccess";
	}
	
	@RequestMapping( "/loginform" )
	public String loginForm() {
		return "/user/loginform";
	}
	
	@RequestMapping( "/checkemail" )
	@ResponseBody
	public Object checkEmail( 
		@RequestParam( value="email", required=true, defaultValue="" ) String email ) {
		UserVo vo = userService.getUser( email );
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", vo == null );
		return map;
	}

	@RequestMapping( "/hello" )
	@ResponseBody
	public String hello() {
		return "hello:안녕";
	}
}
