package com.estsoft.mysite.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String join( @Valid @ModelAttribute UserVo vo, BindingResult result, Model model) {
		if ( result.hasErrors() ) {
//	       // 에러 출력
//	       List<ObjectError> list = result.getAllErrors();
//	       for (ObjectError e : list) {
//	            System.out.println(" ObjectError : " + e );
//	       }
			model.addAllAttributes( result.getModel() );
	        return "/user/joinform";
		}
       userService.join(vo);
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
		return userService.checkEmail(vo, email);
	}
}
