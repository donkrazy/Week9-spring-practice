package com.estsoft.mysite.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	
	public UserVo get( String email ) {
		return sqlSession.selectOne("user.selectByEmail", email );
	}
	
	public UserVo get( Long userNo ) {
		return sqlSession.selectOne("user.selectByNo", userNo);
	}	

	// 보안 = 인증 + 권한
	// 인증(Auth)
	public UserVo get( UserVo vo ) {
		return sqlSession.selectOne("user.selectByEmailAndPassword", vo);
	}
	
	
	public void update( UserVo userVo ) {
		sqlSession.update( "user.update", userVo );
	}
	
	public void insert( UserVo vo ) {
		sqlSession.insert( "user.insert", vo );
	}
}
