package com.ezen.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/user/*")
@Controller
public class UserController {

    private final UserService usv;
    private final BCryptPasswordEncoder bcEncoder;

    @GetMapping("/register")
    public void register() {}
    
    @PostMapping("/register")
    public String register(UserVO uvo) {
    	// encode : 암호화
    	uvo.setPwd(bcEncoder.encode(uvo.getPwd()));
    	log.info(">>> UserVO : {}", uvo);
    	int isOk = usv.registerUser(uvo);
    	return "redirect:/";
    }
    
    @GetMapping("/login")
    public void login() {}
    
    @PostMapping("/login")
    public String login(HttpServletRequest request, RedirectAttributes re) {
    	// 실제 로그인은 security의 필터에서 가져감
    	// 로그인 실패 시 다시 로그인 페이지로 돌아와서 오류 메세지를 전송
    	// 재로그인 유도용
    	log.info(">>> errorMessage : {}", request.getAttribute("errorMessage").toString());
    	
    	re.addAttribute("email", request.getAttribute("email"));
    	re.addAttribute("errorMessage", request.getAttribute("errorMessage"));
    	
		return "redirect:/user/login/";
    }
}