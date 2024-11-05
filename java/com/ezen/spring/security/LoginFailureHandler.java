package com.ezen.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
	
	private String authEmail;
	private String errorMessage;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		// BadCredentialException || InternalAuthenticationServiceException
		setAuthEmail(request.getParameter("email"));
		
		// exception 발생 시 메세지 저장
		if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			// setErrorMessage(exception.getMessage().toString());
			setErrorMessage("ID 혹은 비밀번호가 잘못되었습니다.");
		} else {
			setErrorMessage("error! 관리자에게 문의해 주세요.");
		}
		
		log.info(">>> errorMessage : {}", errorMessage);
		
		request.setAttribute("email", getAuthEmail());
		request.setAttribute("errorMessage", getErrorMessage());
		
		request.getRequestDispatcher("/user/login?error").forward(request, response);
		

	}

}
