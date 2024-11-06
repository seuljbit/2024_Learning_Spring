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

	List<UserVO> getList();

	int modifyPwdEmpty(UserVO uvo);

	int modify(UserVO uvo);

	int remove(String email);

	int removeAuth(String email);

}