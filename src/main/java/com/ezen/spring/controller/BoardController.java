package com.ezen.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.FileHandler;
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
    private final FileHandler fh;

    @GetMapping("/register") 
    public void register() {} // void 리턴 -> 요청 경로 그대로 반환 /board/register => /board/register.jsp

    // 첨부파일 -> multipartFile, multipartFile 여러개-> []
    @PostMapping("/insert")
    public String insert(BoardVO bvo, MultipartFile[] files) {
        log.info(">>> insert bvo : {}", bvo);
        List<FileVO> fileList = null;
        
        if(files[0].getSize() > 0) { 
        	fileList = fh.uploadFiles(files); // 파일의 내용이 있다면 업로드해라
        	log.info("fileList : {}", fileList);
        }
        
        // fules 정보를 이용하여 List<FileVO> 변환하는 핸들러
        // fileHandler -> return List<FileVO> + 파일 저장
        
        BoardDTO bdto = new BoardDTO(bvo, fileList);
        
        int isOk = bsv.insert(bdto);
        log.info(">>> insert : {}", isOk > 0 ? "Ok!" : "Fail");
        
        return "redirect:/"; // redirect:/board/list를 통해 컨트롤러의 매핑 위치로 연결
        //return "redirect:/board/detail?bno=" + bvo.getBno();
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
    public void detail(long bno, Model m, HttpServletRequest request) { // bno에 해당하는 BoardVO 객체를 DB에서 가져와 모델로 전달
    	String path = request.getServletPath();
    	log.info(">>> path : {}", path);
    	BoardDTO bdto = bsv.getDetail(bno);
        m.addAttribute("bdto", bdto);
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