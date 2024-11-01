package com.ezen.spring.service;

import java.util.List;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;

public interface BoardService {
	// int insert(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	// BoardVO getDetail(int bno);
	BoardDTO getDetail(long bno);

	int modify(BoardVO bvo);

	int delete(int bno);

	int getTotal(PagingVO pgvo);

	int insert(BoardDTO bdto);
}
