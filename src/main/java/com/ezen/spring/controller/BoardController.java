package com.ezen.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor // final 필드나 @NonNull 필드만 받는 생성자를 자동으로 주입
@RequestMapping("/board/*")
@Controller
public class BoardController {
    private final BoardService bsv; // 생성자 주입 시 객체는 final로 선언

    @GetMapping("/register") 
    public void register() {} // void 리턴 -> 요청 경로 그대로 반환 /board/register => /board/register.jsp

    @PostMapping("/insert")
    public String insert(BoardVO bvo) {
        log.info(">>> insert bvo : {}", bvo);
        
        int isOk = bsv.insert(bvo);
        log.info(">>> insert : {}", isOk > 0 ? "Ok!" : "Fail");
        
        return "redirect:/"; // redirect:/board/list를 통해 컨트롤러의 매핑 위치로 연결
    }
    
    @GetMapping("/list")
    public String list(Model m, PagingVO pgvo) { // Model 객체를 통해 request.setAttribute() 대체
//      PagingVO pgvo = new PagingVO();
        List<BoardVO> list = bsv.getList(pgvo);
        
        // totalcount 구해서 PagingHandler에 매개변수로 전달
        int totalCount = bsv.getTotal(pgvo);
        
        PagingHandler ph = new PagingHandler(totalCount, pgvo);
        log.info(">>> totalCount : {}", totalCount);
        
        m.addAttribute("list", list);
        m.addAttribute("ph", ph);
        
        return "/board/list";
    }
    
    // @RequestParam("bno") int bno : 파라미터가 여러 개일 경우 이름 명시
    // return void -> 요청 경로 그대로 반환 /board/detail -> /board/detail.jsp
    @GetMapping({"/detail", "/modify"})
    public void detail(int bno, Model m) { // bno에 해당하는 BoardVO 객체를 DB에서 가져와 모델로 전달
        BoardVO bvo = bsv.getDetail(bno);
        m.addAttribute("bvo", bvo);
    }
    
    @PostMapping("/update")
    public String update(BoardVO bvo) {
        int isOk = bsv.modify(bvo);
        log.info(">>> update : {}", isOk > 0 ? "OK!" : "Fail");
        
        // redirect:/board/detail로 이동 x -> 컨트롤러의 detail 매핑으로 이동
        return "redirect:/board/detail?bno=" + bvo.getBno();
    }
    
    @GetMapping("/delete")
    public String delete(int bno) {
        int isOk = bsv.delete(bno);
        log.info(">>> delete : {}", isOk > 0 ? "OK!" : "Fail");
        
        return "redirect:/board/list";
    }
}  