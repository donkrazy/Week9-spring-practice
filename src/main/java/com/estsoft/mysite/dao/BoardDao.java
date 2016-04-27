package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.BoardVo;


@Repository
public class BoardDao {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;

	public BoardVo get( Long boardNo ) {
		BoardVo vo = sqlSession.selectOne("board.selectByNo", boardNo);
		return vo;
	}
	
	//TODO: delete 구현
	public void delete( BoardVo boardVo ) {
		sqlSession.delete("board.delete", boardVo);
	}	
	

	public void updateHits( Long no ) {
		sqlSession.update("board.updateHits", no);
	}
	
	public void updateGroupOrder( BoardVo boardVo ) {
		sqlSession.update("board.updateGroupOrder", boardVo);
	}

	public long getTotalCount( String keyword ) {
		long count = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
		
			String sql = "SELECT count(*) FROM  board a, user b WHERE a.user_no = b.no";
			if( null != keyword && "".equals( keyword ) == false ) {
				sql += ( "  AND ( title LIKE '%" + keyword + "%' OR title LIKE '%" + keyword + "%')" );
			}
		
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery( sql );
			if( rs.next() ) {
				count = rs.getLong( 1 );
			}
			
			return count;
			
		} catch( SQLException ex ) {
			System.out.println( "error: " + ex);
			return count;
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
	}
	
	public List<BoardVo> getList( String keyword, long currentPage, int listSize ) {
		List<BoardVo> list = new ArrayList<BoardVo>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();

			if( null != keyword && "".equals( keyword ) == false ) {
				String sql =
						"      SELECT  a.no, a.title, b.name, b.no, a.hits, DATE_FORMAT(reg_date, '%Y-%m-%d %p %h:%i:%s'), depth" +
						"       FROM  board a," +
						"                 user b" +
						"      WHERE a.user_no = b.no" +
						"         AND (a.title LIKE ? OR a.content LIKE ?)" +
						" ORDER BY  group_no DESC, order_no ASC" + 
						"       LIMIT  ?, ?";
				
				pstmt = conn.prepareStatement( sql );
				pstmt.setString( 1, "%" + keyword + "%" );
				pstmt.setString( 2, "%" + keyword + "%" );
				pstmt.setLong( 3, ( currentPage - 1 ) * listSize ); 
				pstmt.setInt( 4, listSize );
			} else {
				String sql =
						"     SELECT  a.no, a.title, b.name, b.no, a.hits, DATE_FORMAT(reg_date, '%Y-%m-%d %p %h:%i:%s'), depth" +
						"      FROM  board a," +
						"                user b" +
						"      WHERE a.user_no = b.no" +
						" ORDER BY  group_no DESC, order_no ASC" + 
						"       LIMIT  ?, ?";
				pstmt = conn.prepareStatement( sql );
				pstmt.setLong( 1, ( currentPage - 1 ) * listSize ); 
				pstmt.setInt( 2, listSize );
			}
			
			rs = pstmt.executeQuery();
			while( rs.next() ) {
				Long no = rs.getLong( 1 );
				String title = rs.getString( 2 );
				String userName = rs.getString( 3 );
				Long userNo = rs.getLong( 4 );
				Integer hits = rs.getInt( 5 );
				String regDate = rs.getString( 6 );
				Integer depth = rs.getInt( 7 );

				BoardVo vo = new BoardVo();
				vo.setNo( no );
				vo.setTitle( title );
				vo.setUserName( userName );
				vo.setUserNo( userNo );
				vo.setHits( hits );
				vo.setRegDate( regDate );
				vo.setDepth( depth );
				
				list.add( vo );
			}
			
			return list;
		} catch( SQLException ex ) {
			System.out.println( "error: " + ex);
			return list;
		} finally {
			try{
				if( rs != null ) {
					rs.close();
				}
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException ex ) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public long insert( BoardVo boardVo ) {
		Long no = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs  = null;
		try{
			conn = dataSource.getConnection();
			if( null == boardVo.getGroupNo() ) {
				// 새글 등록
				String sql = "INSERT INTO board VALUES( null, ?, now(), ?, ?, (select ifnull( max( group_no ), 0 ) + 1 from board as b), 1, 0, 0 )";  
				pstmt = conn.prepareStatement( sql );
				pstmt.setString( 1, boardVo.getTitle() );
				pstmt.setString( 2, boardVo.getContent() );
				pstmt.setLong( 3, boardVo.getUserNo() );
				}
			else {
				// 답글 등록
				String sql = "INSERT INTO board VALUES( null, ?, now(), ?, ?, ?, ?, ?, 1)";
				pstmt = conn.prepareStatement( sql );
				pstmt.setString( 1, boardVo.getTitle() );
				pstmt.setString( 2, boardVo.getContent() );
				pstmt.setLong( 3, boardVo.getUserNo() );
				pstmt.setInt( 4, boardVo.getGroupNo() );
				pstmt.setInt( 5, boardVo.getOrderNo() );
				pstmt.setInt( 6, boardVo.getDepth() );
			}
			pstmt.executeUpdate();
			// return last insert ID
			stmt = conn.createStatement();
			rs = stmt.executeQuery( "SELECT LAST_INSERT_ID()" );
			if( rs.next() ) {
				no = rs.getLong( 1 );
			}
		}
		 catch( SQLException ex ) {
			System.out.println( "error:" + ex );
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException ex ) {
				ex.printStackTrace();
			}
		}
		return no;
	}
	
}
