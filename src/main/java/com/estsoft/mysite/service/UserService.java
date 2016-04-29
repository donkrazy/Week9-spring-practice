package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	// @Autowired
	// private MailSender mailSender;
	
	public void join( UserVo vo ) {
		userDao.insert(vo);
		// 메일보내기
		// ..
	}
	public Map<String, Object> checkEmail(UserVo vo, String email){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", vo == null );
		return map;
	}
	
	public UserVo login( UserVo vo ) {
		UserVo authUser = userDao.get( vo );
		return authUser;
	}
	
	public UserVo getUser( String email ) {
		UserVo vo = userDao.get( email );
		return vo;
	}
}
