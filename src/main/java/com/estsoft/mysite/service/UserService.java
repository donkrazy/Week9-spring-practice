package com.estsoft.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.UserDao;
import com.estsoft.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public void join( UserVo vo ) {
		userDao.insert(vo);
	}
	
	public Boolean login( UserVo vo ) {
		UserVo authUser = userDao.get( vo );
		return authUser != null;
	}
}
