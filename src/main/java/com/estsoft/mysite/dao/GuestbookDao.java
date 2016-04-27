package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.GuestbookVo;

@Repository
public class GuestbookDao {
	
	@Autowired
	private DataSource dataSource;
	
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
		List<GuestbookVo> list = new ArrayList<GuestbookVo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			String sql = 
				"      SELECT no, name, DATE_FORMAT( reg_date, '%Y-%m-%d %p %h:%i:%s' ), message" +
				"       FROM guestbook" +
				" ORDER BY reg_date desc" + 
				"       LIMIT " + (page-1)*5 + ", 5";
			rs = stmt.executeQuery( sql );
			while( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				String regDate = rs.getString( 3 );
				String message = rs.getString( 4 );
				GuestbookVo vo = new GuestbookVo();
				vo.setNo( no );
				vo.setName( name );
				vo.setRegDate( regDate );
				vo.setMessage( message );
				list.add( vo );
			}
		} catch( SQLException ex ) {
			System.out.println( "error: " + ex);
		} finally {
			try{
				if( rs != null ) {
					rs.close();
				}
				if( stmt != null ) {
					stmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException ex ) {
				ex.printStackTrace();
			}
		}
			
		return list;
	}
}
