package com.ezen.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.spring.dao.CommentDAO;
import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	private final CommentDAO cdao;

	@Override
	public int post(CommentVO cvo) {
		return cdao.post(cvo);
	}

//	@Override
//	public List<CommentVO> getList(long bno) {
//		return cdao.getList(bno);
//	}

	@Override
	public int modify(CommentVO cvo) {
		return cdao.modify(cvo);
	}

	@Override
	public int delete(long cno) {
		return cdao.delete(cno);
	}

	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		// ph 객체 안에 cmtList, totalCount 구해오기
		List<CommentVO> cmtList = cdao.getList(bno, pgvo);
		int totalCount = cdao.getTotalCount(bno);
		PagingHandler ph = new PagingHandler(totalCount, pgvo, cmtList);
		
		return ph;
	}
	

}
