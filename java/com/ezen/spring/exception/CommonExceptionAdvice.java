package com.ezen.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CommonExceptionAdvice {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handler404(NoHandlerFoundException ex, Model m) {
		log.info(">>> exception : {}", ex.getMessage());
		m.addAttribute("exception", ex.getMessage());
		
		return "custom404";
	}
	
//	@ExceptionHandler(Exception.class)
//	public Stirng exception(Exception ex, Model m) {
//		return 새로 페이지 만들어서 연결
//	}
}
