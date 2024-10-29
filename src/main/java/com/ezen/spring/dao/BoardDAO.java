package com.ezen.spring.dao;

import java.util.List;

import com.ezen.spring.domain.BoardVO;

public interface BoardDAO {
	public int insert(BoardVO bvo);

	public List<BoardVO> getList();
}
