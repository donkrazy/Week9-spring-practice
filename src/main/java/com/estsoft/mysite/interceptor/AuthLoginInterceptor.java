package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.estsoft.mysite.service.UserService;
import com.estsoft.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		// UserService userService = new UserService(); 새로 세팅하는건 힘들다
		
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		UserService userService = applicationContext.getBean( UserService.class );
		UserVo userVo = new UserVo();
		userVo.setEmail(email);
		userVo.setPassword(password);
		UserVo authUser = userService.login(userVo);
		if(authUser == null){
			response.sendRedirect( request.getContextPath()+"/user/loginform" );
			return false;
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser );
		String nextURL = request.getParameter("next");
		response.sendRedirect( request.getContextPath()+nextURL );
		return false;
	}

}
