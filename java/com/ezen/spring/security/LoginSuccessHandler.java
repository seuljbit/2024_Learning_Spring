package com.ezen.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.ezen.spring.dao.UserDAO;

import lombok.Getter;
import lombok.Setter;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserDAO udao;
	
	@Getter
	@Setter
	private String authEmail;

	@Getter
	@Setter
	private String authUrl; // 로그인 성공 후 이동 위치
	
	// redirect 데이터를 가지고 리다이렉트 경로로 이동하는 역할
	private RedirectStrategy redStr = new DefaultRedirectStrategy();
	
	// 세션의 캐쉬 정보
	private RequestCache reqCache = new HttpSessionRequestCache();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 1. lastLogin 기록(update), 로그인 성공 후 경로 지정
		// authentication -> username -> getName()
		setAuthEmail(authentication.getName());
		
		int isOk = udao.updateLastLogin(getAuthEmail());
		
		setAuthUrl("/board/list");
		
		// 2-1 시큐리티에서 로그인 시도 실패 시 기록이 남게 됨
		// 2-2 로그인 시도 성공 시 기존에 실패했던 기록 삭제
		// 세선 가져오기
		HttpSession ses = request.getSession();
		if(isOk == 0 || ses == null) {
			return;
		} else { // removeAttribute : 세션의 객체 삭제
			ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		
		// 3. 로그인 직전 URL 연결
		SavedRequest saveRequest = reqCache.getRequest(request, response);
		redStr.sendRedirect(request, response,
				saveRequest != null ? saveRequest.getRedirectUrl() : getAuthUrl());
		
	}
}
