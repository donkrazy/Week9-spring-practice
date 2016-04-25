package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.db.DBConnection;
import com.estsoft.mysite.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private DBConnection dbConnection;
	
	public UserVo get( String email ) {
		UserVo vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			
			String sql = "SELECT no, email FROM user WHERE email=?";
			pstmt = conn.prepareStatement( sql );
			
			pstmt.setString( 1, email );
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				vo = new UserVo();
				vo.setNo( rs.getLong( 1 ) );
				vo.setEmail( rs.getString( 2 ) );
			}
			
			return vo;
			
		} catch( SQLException ex ) {
			System.out.println( "error:" + ex );	
			return null;
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
			}catch( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
	
	public UserVo get( Long userNo ) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT no, name, email, gender FROM user WHERE no=?"; 
			pstmt = conn.prepareStatement( sql );
			pstmt.setLong( 1, userNo );

			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				String email = rs.getString( 3 );
				String gender = rs.getString( 4 );
				
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			return userVo;
		} catch (SQLException e) {
			System.out.println( "error:" + e );
			return null;
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
			}catch( SQLException e ) {
				e.printStackTrace();
			}
		}
	}	

	// 보안 = 인증 + 권한
	// 인증(Auth)
	public UserVo get( String email, String password ) {
		return null;
	}
	
	public UserVo get( UserVo vo ) {
		UserVo userVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbConnection.getConnection();
			String sql = 
	"SELECT no, name, email FROM user WHERE email=? AND passwd=password(?)"; 
			pstmt = conn.prepareStatement( sql );
			pstmt.setString( 1, vo.getEmail() );
			pstmt.setString( 2, vo.getPassword() );

			rs = pstmt.executeQuery();
			if( rs.next() ) {
				Long no = rs.getLong( 1 );
				String name = rs.getString( 2 );
				String email = rs.getString( 3 );
				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
			}
			
			return userVo;
		} catch (SQLException e) {
			System.out.println( "error:" + e );
			return null;
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
			}catch( SQLException e ) {
				e.printStackTrace();
			}
		}
	}

	public void update( UserVo userVo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();
			if( "".equals( userVo.getPassword() ) ) {
				String sql = "UPDATE user SET  name=?, gender=? WHERE no = ?";
				pstmt = conn.prepareStatement( sql );
				
				pstmt.setString( 1, userVo.getName() );
				pstmt.setString( 2,  userVo.getGender() );
				pstmt.setLong( 3,  userVo.getNo() );
			} else {
				String sql = "UPDATE user SET  name=?, gender=?, passwd=password(?) WHERE no = ?";	
				pstmt = conn.prepareStatement( sql );
				pstmt.setString( 1, userVo.getName() );
				pstmt.setString( 2,  userVo.getGender() );
				pstmt.setString( 3,  userVo.getPassword() );
				pstmt.setLong( 4,  userVo.getNo() );
			}
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println( "error:" + e );	
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
	
	public void insert( UserVo vo ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbConnection.getConnection();
			String sql = 
	"INSERT INTO user VALUES (null, ?, ?, password(?), ? )";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString( 1, vo.getName() );
			pstmt.setString( 2, vo.getEmail() );
			pstmt.setString( 3, vo.getPassword() );
			pstmt.setString( 4,  vo.getGender() );
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println( "error:" + e );	
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException e ) {
				e.printStackTrace();
			}
		}
	}
}
