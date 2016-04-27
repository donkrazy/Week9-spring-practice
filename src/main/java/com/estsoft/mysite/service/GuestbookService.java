package com.estsoft.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.GuestbookDao;
import com.estsoft.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	GuestbookDao guestbookDao;

	public List<GuestbookVo> getMessageList() {
		return guestbookDao.getList();
	}
	
	public boolean deleteMessage( GuestbookVo vo ) {
		return guestbookDao.delete( vo ) == 1;
	}
	
	public Object deleteMessage( GuestbookVo vo, boolean isAjax) {
		int countDeleted = guestbookDao.delete( vo );
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		if(countDeleted == 1){
			map.put("data", vo.getNo() );
		}
		else{
			map.put("data", null );
		}
		return map; 
	}
	
	public boolean insertMessage( GuestbookVo vo ) {
		Long no = guestbookDao.insert(vo);
		return no != 0;
	}
	
	public Map<String, Object> insertMessage( GuestbookVo vo, boolean isAjax ) {
		long no = guestbookDao.insert(vo);
		vo = guestbookDao.get(no);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", vo );
		return map;
	}
	
	public Map<String, Object> getList(int page){
		List<GuestbookVo> list = guestbookDao.getList(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "result", "success" );
		map.put( "data", list );
		return map;
	}
}