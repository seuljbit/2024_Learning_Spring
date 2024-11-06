package com.ezen.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.spring.dao.UserDAO;
import com.ezen.spring.domain.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserDAO udao;

	@Transactional
	@Override
	public int register(UserVO uvo) {
		// TODO Auto-generated method stub
		int isOk = udao.insert(uvo);
		return udao.insertAuthInit(uvo.getEmail());
	}

	@Transactional
	@Override
	public List<UserVO> getList() {
		List<UserVO> userList = udao.getList();
		for(UserVO uvo : userList) {
			uvo.setAuthList(udao.selectAuths(uvo.getEmail()));
		}
		return userList;
	}

	@Override
	public int modifyPwdEmpty(UserVO uvo) {
		return udao.modifyPwdEmpty(uvo);
	}

	@Override
	public int modify(UserVO uvo) {
		return udao.modify(uvo);
	}

	@Transactional
	@Override
	public int remove(String email) {
		int isOk = udao.removeAuth(email);
		return udao.remove(email);
	}
}