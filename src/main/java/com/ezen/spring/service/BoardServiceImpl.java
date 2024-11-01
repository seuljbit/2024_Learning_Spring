package com.ezen.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.spring.dao.BoardDAO;
import com.ezen.spring.dao.FileDAO;
import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	private final BoardDAO bdao;
	private final FileDAO fdao;

//	@Override
//	public int insert(BoardVO bvo) {
//		return bdao.insert(bvo);
//	}

	@Override
	public int insert(BoardDTO bdto) { // bvo + file
		// bvo 먼저 insert 하고 난 후, bno를 DB에서 빼와야 함 > fvo를 DB에 저장
		int isOk = bdao.insert(bdto.getBvo());
		
		if(bdto.getFileList() == null) { // 첨부 파일이 없다면?
			return isOk;
		}
		
		if(isOk > 0 && bdto.getFileList().size() > 0) { // 첨부 파일이 있다면?
			// bno setting
			long bno = bdao.getOneBno(); // 가장 마지막에 저장된 bno
			
			for(FileVO fvo : bdto.getFileList()) {
				fvo.setBno(bno);
				isOk *= fdao.insertFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		return bdao.getList(pgvo);
	}

//	@Override
//	public BoardDTO getDetail(int bno) {
//	    bdao.incrementReadCount(bno);
//	    return bdao.getDetail(bno);
//	}

	@Override
	public BoardDTO getDetail(long bno) {
		BoardVO bvo = bdao.getDetail(bno);
		List<FileVO> fileList = fdao.getList(bno);
		
		BoardDTO bdto = new BoardDTO(bvo, fileList);
		
		return bdto;
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
