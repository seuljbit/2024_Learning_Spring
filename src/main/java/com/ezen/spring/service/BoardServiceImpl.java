package com.ezen.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.spring.dao.BoardDAO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	private final BoardDAO bdao;

	@Override
	public int insert(BoardVO bvo) {
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		return bdao.getList(pgvo);
	}

	@Override
	public BoardVO getDetail(int bno) {
		return bdao.getDetail(bno);
	}

	@Override
	public int modify(BoardVO bvo) {
		// TODO Auto-generated method stub
		return bdao.update(bvo);
	}

	@Override
	public int delete(int bno) {
		return bdao.delete(bno);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		return bdao.getTotal(pgvo);
	}
	
	
}
