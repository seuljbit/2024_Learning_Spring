package com.ezen.spring.handler;

import java.util.List;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingHandler {
/* 한 페이지에 표시되는 페이지네이션 버튼 수: 10개 (qty)
 * 한 페이지에 표시되는 페이지네이션 시작 번호 : 1 11 21 ... 181
 * 한 페이지에 표시되는 페이지네이션 끝 번호 : 10 20 30 ... 181
 * 이전, 다음의 노출 여부 : boolean
 * 전체 페이지 수 : DB에서 조회해와야 하는 값(매개변수로 받아오기)
 * 현재 페이지 번호 : pagingVO의 pageNO 사용(매개변수로 받아오기)
 * 최종 페이지 끝 번호 : 181 */
	
	private int qty; // 한 페이지에 나오는 페이지네이션 개수
	private int startPage; // 한 페이지에 나오는 페이지네이션 시작 번호
	private int endPage; // 한 페이지에 나오는 페이지네이션 끝 번호
	private boolean prev;
	private boolean next;
	
	private int totalCount; // 전체 페이지 수 : DB에서 조홰해와야 하는 값(매개변수로 받아오기)
	private PagingVO pgvo; // 현재 페이지 번호 : pagingVO pageNO 사용(매개변수로 받아오기)
	
	private int realEndPage;
	
	private List<CommentVO> cmtList;
	
	// 생성자에서 모든 값들이 계산되어 설정되어야 함
	public PagingHandler(int totalCount, PagingVO pgvo) {
		this.qty = 10;
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		// 1~10 / 11~20 / 21~30 ...
		// pageNo => 1 2 3 ... 10 -> 1~10 ----> endPage는 변함없이 10
		// pageNo => 11 12 13 ... 20 -> 11~20 ----> endPage는 변함없이 20
		
		// 1 / 10 -> 0.1 결과를 올림(1) -> 1 * 10 -> 10
		// 11 / 10 -> 1.1 결과를 올림(2) -> 2 * 10 -> 20

        // endPage : 현재 페이지와 qty를 사용하여 endPage 계산
        this.endPage = (int) (Math.ceil(pgvo.getPageNo() / (double) qty) * qty);
        
        // startPage : endPage에서 qty를 빼고 + 1
        this.startPage = this.endPage - (qty - 1);
        
        // realEndPage(실제 마지막 페이지 번호 계산) : 전체 글 수 / 한 페이지에 표시되는 게시글 수
        // 11 / 10 -> 2페이지 1.1(올림) -> 2
        this.realEndPage = (int) (Math.ceil( totalCount / (double) pgvo.getQty()));
        
        // 이전, 다음 페이지 그룹 여부 설정
        this.prev = this.startPage > 1; // 1  11  21  31
        this.next = this.endPage < this.realEndPage;
        
        // 실제 마지막 페이지 번호에 따른 endPage 조정
        if (this.endPage > this.realEndPage) { 
            this.endPage = this.realEndPage;
            // endPage가 realEndPage보다 더 크면 동일하게 해서 다음으로 넘어가지 않게 함
        }
	}
	
	// 댓글 페이지용 생성자
	public PagingHandler(int totalCount, PagingVO pgvo, List<CommentVO> cmtList) {
		this(totalCount, pgvo);
		this.cmtList = cmtList;
	}
}
