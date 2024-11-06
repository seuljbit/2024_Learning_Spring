package com.ezen.spring.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/**")
@Controller
public class UserController {
	
	private final UserService usv;
	private final BCryptPasswordEncoder bcEncoder;
	
	// mapping 경로와 jsp의 경로가 같으면 void 처리 가능
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(UserVO uvo) {
		log.info(">>>> User VO >> {}", uvo);
		
		uvo.setPwd(bcEncoder.encode(uvo.getPwd())); // encode : 암호화 
		//log.info(">>>> User VO >> {}", uvo);
		
		int isOk = usv.register(uvo);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")
	public String login(HttpServletRequest request, RedirectAttributes re) {
		// 실제 로그인은 Security의 필터에서 가져감. 
		// 로그인 실패시 다시 로그인 페이지로 돌아와 오류 메시지를 전송
		// 재 로그인을 유도
		log.info(">>> errorMessage >> {}", request.getAttribute("errorMessage").toString());
		
		re.addAttribute("email", request.getAttribute("email"));
		re.addAttribute("errorMessage", request.getAttribute("errorMessage"));
		
		return "redirect:/user/login";
	}
	
	@GetMapping("/list")
	public void list(Model m) {
		List<UserVO> userList = usv.getList();
		m.addAttribute("userList", userList);
	}
	
	@GetMapping("/modify")
	public void modify() {}
	
	@PostMapping("/modify")
	public String modify(UserVO uvo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes re) {
		log.info(">>>> modify uvo >> {}", uvo);
		int isOk = 0;
		
		if(uvo.getPwd().isEmpty() || uvo.getPwd().length() == 0) { //비번 없이 업데이트 진행
			isOk = usv.modifyPwdEmpty(uvo);
		}else { // 비번 암호화 후 업데이트 진행
			uvo.setPwd(bcEncoder.encode(uvo.getPwd()));
			isOk = usv.modify(uvo);
		}
		
		//로그아웃 => index로 돌아가기
		logout(request, response);
		if(isOk > 0) {
			re.addFlashAttribute("modify_msg", "ok");			
		}else {
			re.addFlashAttribute("modify_msg", "fail");	
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/remove")
	public String remove(HttpServletRequest request, HttpServletResponse response, Principal principal, RedirectAttributes re) {
		log.info(principal.toString());
		String email = principal.getName();
		
		int isOk = usv.remove(email);
		
		if(isOk > 0) {
			re.addFlashAttribute("remove_msg", "ok");			
		}else {
			re.addFlashAttribute("remove_msg", "fail");	
		}
		
		logout(request, response);
		
		return "redirect:/";
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		// 내가 로그인 한 시큐리티의 authentication 객체
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(request, response, authentication);
	}

}