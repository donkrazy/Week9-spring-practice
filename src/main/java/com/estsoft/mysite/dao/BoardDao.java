package com.estsoft.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.BoardVo;


@Repository
public class BoardDao {
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
	
	public long insert( BoardVo boardVo ) {
		sqlSession.insert("board.insert", boardVo);
		return boardVo.getNo();
	}

	public long getTotalCount( String keyword ) {
		long count = sqlSession.selectOne("board.getTotalCount", keyword);
		return count;
	}
	
	public List<BoardVo> getList( String keyword, long page, int listSize ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "keyword", keyword );
		map.put( "page", page );
		map.put( "listSize", listSize );
		List<BoardVo> list = sqlSession.selectList("board.getList", map);
		return list;
	}
}
