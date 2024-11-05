package com.ezen.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ezen.spring.security.CustomAuthUserService;
import com.ezen.spring.security.LoginFailureHandler;
import com.ezen.spring.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	// 비밀번호를 암호화하는 객체 : PasswordEncoder 빈 생성
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// SuccessHandler 객체 빈 생성 -> 사용자 커스텀 객체
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
	}
	
	// FailureHandler 객체 빈 생성 -> 사용자 커스텀 객체
	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new LoginFailureHandler();
	}
	
	// UserDetail 객체 빈 생성 -> 사용자 커스텀 객체
	@Bean
	public UserDetailsService customDetailsService() {
		return new CustomAuthUserService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 인증용 객체 생성 매니저 설정
		auth.userDetailsService(customDetailsService()).passwordEncoder(bcPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 화면에서 설정되는 권한에 따른 주소 맵핑 설정
		// 화면에서 오는 로그인 정보 구성
		// csrf() : 공격에 대한 설정 풀기
		
		http.csrf().disable();
		
		// 권한 승인 요청
		// antMatchers : 접근을 허용하는 경로
		// permitAll : 누구나 접근이 가능한 경로
		// hasRole('권한') : 해당 권한 확인
		// authenticated() : 인증된 사용자만 가능한 경로
		// USER, ADMIN, MANAGER
		
		http.authorizeRequests().antMatchers("/user/list").hasRole("ADMIN")
			.antMatchers("/","/user/login","/user/register","/board/list","/board/detail","/upload/**","/comment/**","/resources/**")
			.permitAll().anyRequest().authenticated();
		
		// 로그인 페이지 구성 : id -> email  pw -> pwd
		// Controller에 주소 요청 맵핑은 같이 이어야 함(필수)
		http.formLogin().usernameParameter("email").passwordParameter("pwd").loginPage("/user/login")
		//.defaultSuccessUrl("/").failureUrl("/");
		.successHandler(authSuccessHandler()).failureHandler(authFailureHandler());
		
		// 로그아웃 구성 : method = "post" (?)
		http.logout().logoutUrl("/user/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/");
		
	}
	
}
