package com.ezen.spring.dao;

import java.util.List;

import com.ezen.spring.domain.AuthVO;
import com.ezen.spring.domain.UserVO;

public interface UserDAO {

	int insert(UserVO uvo);

	int insertAuthInit(String email);

	UserVO selectEmail(String username);

	List<AuthVO> selectAuths(String username);

	int updateLastLogin(String authEmail);
}
