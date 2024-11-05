package com.ezen.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ezen.spring.dao.UserDAO;
import com.ezen.spring.domain.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthUserService implements UserDetailsService {
	
	// 인증용 user name, password를 가지고 인증용 토큰 생성 -> DB에서 확인
	// return UserDetail : 인증 객체 리턴
	
	@Autowired
	private UserDAO udao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserVO uvo = udao.selectEmail(username); // username : 로그인을 시도하는 email
		
		if(uvo == null) { // 만약 아이디가 없거나 잘못 입력했다면?
			throw new UsernameNotFoundException(username);
		}
		
		uvo.setAuthList(udao.selectAuths(username));
		log.info(">>> user Details : {}", uvo);
		
		return new AuthUser(uvo);
	}

}
