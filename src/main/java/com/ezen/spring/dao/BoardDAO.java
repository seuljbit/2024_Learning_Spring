package com.ezen.spring.dao;

import java.util.List;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;

public interface BoardDAO {
	int getTotal = 0;

	public int insert(BoardVO bvo);

	public List<BoardVO> getList(PagingVO pgvo);

	public BoardVO getDetail(int bno);

	public int update(BoardVO bvo);

	public int delete(int bno);

	public int getTotal(PagingVO pgvo);
}