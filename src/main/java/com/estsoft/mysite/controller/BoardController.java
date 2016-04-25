package com.estsoft.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.estsoft.mysite.service.BoardService;
import com.estsoft.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	//FIXME @RequestMapping("/") 이건 왜 안되지?
	@RequestMapping("")
	public String index(@RequestParam(value="kwd", defaultValue="") String kwd,  @RequestParam(value="p", defaultValue="1") String page, Model model) {
		Map<String, Object> map = boardService.list(kwd, page);
		model.addAttribute( "map", map );
		return "/board/list";
	}
	
	@RequestMapping("/view/{no}")
	public String view(@PathVariable( "no" ) Long no, Model model){
		BoardVo boardVo = boardService.view(no);
		model.addAttribute("boardVo", boardVo);
		return "/board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String writeform(){
		return "/board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo, HttpSession session){
		long no = boardService.write(vo, session);
		if(no==-1){
			return "redirect:/board/list";
		}
		//TODO return "redirect:/board/view/"+no;
		return "redirect:/board/";
	}
	
	@RequestMapping(value="/write/{no}", method=RequestMethod.GET)
	public String reply(@PathVariable( "no" ) Long no, Model model){
		BoardVo boardVo = boardService.get(no);
		model.addAttribute("boardVo", boardVo);
		return "/board/reply";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modifyform(@PathVariable( "no" ) Long no, Model model){
		BoardVo boardVo = boardService.get(no);
		model.addAttribute("boardVo", boardVo);
		return "/board/modify";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(@PathVariable( "no" ) Long no, Model model){
		BoardVo boardVo = boardService.view(no);
		model.addAttribute("boardVo", boardVo);
		//TODO: 여기도 글번호 view로 날려주면 좋을 것 같다
		return "/board/";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteform(@PathVariable( "no" ) Long no, Model model){
		//
		return "/board/delete";
	}
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@PathVariable( "no" ) Long no, Model model){
		//
		return "/board/";
	}
	
}
