package com.ezen.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // final 필드나 @NonNull 필드만 받는 생성자를 자동 주입
@RequestMapping("/board/*")
@Controller
public class BoardController {
	private final BoardService bsv; // 생성자 주입 시 객체는 final로 생성
	
	@GetMapping("/register") 
	public void register() {} // return void -> 온 경로 그대로 리턴 /board/register => /board/register.jsp

	@PostMapping("/insert")
	public String insert(BoardVO bvo) {
		log.info(">>> insert bvo : {}", bvo);
		
		int isOk = bsv.insert(bvo);
		log.info(">>> insert : {}", isOk > 0 ? "Ok!" : "Fail");
		
		// /WEN-INF/views/.jsp x
		// 컨트롤러의 mapping 위치로 연결할 때 redirect:/board/lost
		return "redirect:/";
	}
	
	@GetMapping("/list")
	public String list(Model m) {
		// request.setArrtbute() = Model 객체가 해당 일을 대신 해줌
		List<BoardVO> list = bsv.getList();
		m.addAttribute("list", list);
		return "/board/list";
	}
}
