package com.ezen.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.spring.dao.BoardDAO;
import com.ezen.spring.dao.CommentDAO;
import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService{
	
	private final CommentDAO cdao;
	private final BoardDAO bdao;


	@Override
	public int post(CommentVO cvo) {
		// TODO Auto-generated method stub
		int isOk = cdao.post(cvo);
		if(isOk >0) {
			isOk *= bdao.cmtQtyUpdate(cvo.getBno(), 1);
		}
		return isOk;
	}

//	@Override
//	public List<CommentVO> getList(long bno) {
//		// TODO Auto-generated method stub
//		return cdao.getList(bno);
//	}

	@Override
	public int modify(CommentVO cvo) {
		// TODO Auto-generated method stub
		return cdao.modify(cvo);
	}

	@Override
	public int delete(long cno, long bno) {
		int isOk = cdao.delete(cno);
		if(isOk > 0) {
			isOk *= bdao.cmtQtyUpdate(bno, -1);
		}
		return isOk;
	}

	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		// ph 객체안에 cmtList / totalCount 구해오기
		List<CommentVO> cmtList = cdao.getList(bno, pgvo);
		int totalCount = cdao.getTotalCount(bno);
		PagingHandler ph = new PagingHandler(totalCount, pgvo, cmtList);
		return ph;
	}

}
