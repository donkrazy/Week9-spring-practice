package com.estsoft.mysite.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	final static private int PAGES = 5;
	
	@Autowired
	private SqlSession sqlSession;
	
	public GuestbookVo get( Long no ) {
		GuestbookVo vo = sqlSession.selectOne("guestbook.selectByNo", no);
		return vo;
	}
	
	public Long insert( GuestbookVo vo ) {
		sqlSession.insert("guestbook.insert", vo);
		return vo.getNo();
	}
	
	public int delete( GuestbookVo vo ) {
		int countDeleted = sqlSession.delete("guestbook.delete", vo);
		return countDeleted;
	}
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = sqlSession.selectList("guestbook.selectList");
		return list;
	}

	public List<GuestbookVo> getList( int page ) {
		List<GuestbookVo> list = sqlSession.selectList("guestbook.selectListPage", PAGES*(page-1));
		return list;
	}
}
