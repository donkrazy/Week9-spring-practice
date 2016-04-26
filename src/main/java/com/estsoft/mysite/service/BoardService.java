package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.BoardDao;
import com.estsoft.mysite.vo.BoardVo;
import com.estsoft.mysite.vo.UserVo;
import com.estsoft.utils.WebUtil;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	private static final int SIZE_LIST = 7;     // 리스팅되는 게시물의 수
	private static final int SIZE_PAGE = 5; // 페이지 리스트에서 표시되는 페이지 수
	
	public Map<String, Object>  list(String keyword, String page){
		long currentPage = 1; 
		// 페이징 정보
		if( page != null && WebUtil.isNumeric( page ) ) {
			currentPage = Long.parseLong( page );
		}
		long totalCount = boardDao.getTotalCount( keyword );
		long totalPage = (long)Math.ceil( (double)totalCount / SIZE_LIST );
		if( currentPage < 1 || currentPage > totalPage ) {
			currentPage = 1;
		}
		long firstPage = ( (long)Math.ceil( (double)currentPage / SIZE_PAGE  ) - 1 ) * SIZE_PAGE + 1;
		long lastPage = firstPage + SIZE_PAGE - 1;
		if( lastPage > totalPage ) {
			lastPage = totalPage;
		}
		long prevPage = 0;
		if( firstPage > SIZE_PAGE ) {
			prevPage = firstPage - 1;
		}
		long nextPage = 0;
		if( lastPage < totalPage ) {
			nextPage = lastPage + 1;
		}
		// 리스트 가져오기
		List<BoardVo> list = boardDao.getList( keyword, currentPage, SIZE_LIST  );

		// 포워딩
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "list", list );
		map.put( "totalCount",  totalCount);
		map.put( "sizeList",  SIZE_LIST );
		map.put( "keyword", keyword );
		map.put( "firstPage", firstPage );
		map.put( "lastPage", lastPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put( "currentPage", currentPage );
		return map;
	}
	
	
	public BoardVo get(long no, boolean view){
		if(view){
			boardDao.updateHits( no );
			BoardVo boardVo = boardDao.get(  no );
			return boardVo;
		}
		else{
			BoardVo boardVo = boardDao.get(no);
			return boardVo;
		}
	}
	
	public long write(BoardVo vo, HttpSession session){
		// 인증 유무 체크
		if( session == null ) {
			return 0;
		}
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			return 0;
		}
		vo.setUserNo( authUser.getNo() );
		if( vo.getGroupNo() != null ) {
			//vo.setGroupNo( vo.getGroupNo() );
			vo.setOrderNo( vo.getOrderNo()  + 1 );
			vo.setDepth( vo.getDepth()  + 1 );
			boardDao.updateGroupOrder( vo );
		}
		return boardDao.insert(vo);
	}
}
