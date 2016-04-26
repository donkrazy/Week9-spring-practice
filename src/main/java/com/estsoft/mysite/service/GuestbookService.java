package com.estsoft.mysite.service;

import java.util.List;

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
	
	public boolean insertMessage( GuestbookVo vo ) {
		Long no = guestbookDao.insert(vo);
		return no != 0;
	}
}