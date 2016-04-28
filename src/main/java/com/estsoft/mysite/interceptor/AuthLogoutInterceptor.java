package com.estsoft.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthLogoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession httpSession = request.getSession();
		if(httpSession != null){
			httpSession.removeAttribute("authUser");
			httpSession.invalidate();
		}
		String nextURL = request.getParameter("next");
		response.sendRedirect( request.getContextPath()+nextURL );
		return false;
	}
}
