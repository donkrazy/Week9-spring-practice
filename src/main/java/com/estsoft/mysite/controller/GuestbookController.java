package com.estsoft.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.estsoft.mysite.service.GuestbookService;
import com.estsoft.mysite.vo.GuestbookVo;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
	@Autowired
	GuestbookService guestbookService;
	
	@RequestMapping( "/ ")
	public String index( Model model ) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute( "list", list );
		return "/guestbook/list";
	}
	
	@RequestMapping( value="/delete/{no}", method=RequestMethod.GET )
	public String deleteform( @PathVariable( "no" ) Long no, Model model ) {
		model.addAttribute( "no", no );
		return "/guestbook/deleteform";
	}
	
	@RequestMapping( value="/delete", method=RequestMethod.POST  )
	public String delete( @ModelAttribute GuestbookVo vo ) {
		guestbookService.deleteMessage(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping( value="/add", method=RequestMethod.POST  )
	public String add( @ModelAttribute GuestbookVo vo ) {
		guestbookService.insertMessage(vo);
		return "redirect:/guestbook";
	}
}
