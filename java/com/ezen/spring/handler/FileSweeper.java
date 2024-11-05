package com.ezen.spring.handler;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ezen.spring.dao.FileDAO;
import com.ezen.spring.domain.FileVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableScheduling
public class FileSweeper {
	//직접 DB 접속을 해서 처리
	private final FileDAO fdao;
	private final String BASE_PATH = "D:\\_myProject\\_java\\_fileUpload\\";
	
	//매일 정해진 시간에 스케줄러를 실행 
	// 매일 등록된 파일과 (DB) <-> 해당 일자의 폴더에 있는 파일이 일치하는 파일은 남기고.
	//일치하지 않는 파일이 있다면 삭제 (DB에는 없는데, 폴더에는 남아있는 파일이 있다면 삭제)
	// file.delete() - fileRemoveHandler를 사용하여 삭제해도 무방함.
	
	// 스케줄 기록  cron 방식 초 분 시 일 월 요일 년도(생략가능)
	// (cron="59 59 23 * * *") : 매일 23시59분59초에 실행
	@Scheduled(cron="0 15 13 * * *")
	public void fileSweeper() {
		log.info(">>>>> FileSweeper Running Start~!! : {}", LocalDateTime.now());
		// 처리
		
		// db에 등록된 모든 파일 목록을 가져오기
		List<FileVO> dbList = fdao.selectListAllFile();
		
		//D:\_myProject\_java\_fileUpload\2024\11\04\\uuid_파일명
		//D:\_myProject\_java\_fileUpload\2024\11\04\\uuid_th_파일명 => 이미지만 존재
		// 파일경로+파일명을 붙인 (실제 존재해야 하는 파일리스트)
		List<String> currFiles = new ArrayList<String>();
		for(FileVO fvo : dbList) {
			String filePath = fvo.getSaveDir()+File.separator+fvo.getUuid();
			String fileName = fvo.getFileName();
			currFiles.add(BASE_PATH+filePath+"_"+fileName);
			
			//이미지라면 썸네일 경로도 추가  = 이미지 파일은 1로 줌.
			if(fvo.getFileType() > 0) {
				currFiles.add(BASE_PATH+filePath+"_th_"+fileName);
			}
		}
		log.info(">>>>> currFiles >> {}", currFiles);
		
		// 실제 파일 경로를 설정
		LocalDate now = LocalDate.now();
		String today = now.toString();
		today = today.replace("-", File.separator);
		
		//경로를 기반으로 저장되어있는 파일을 검색
		//D:\_myProject\_java\_fileUpload\2024\11\04
		File dir = Paths.get(BASE_PATH+today).toFile();
		//listFiles() : 경로안에 있는 모든 파일을 배열로 리턴
		File[] allFileObject = dir.listFiles();
		log.info(">>>> all file Object >> {}", allFileObject.toString());
		
		//실제 저장되어있는 파일목록과, DB의 존재 파일을 비교하여 DB에 없는 파일은 삭제 진행
		for(File file : allFileObject) {
			String storedFileName = file.toPath().toString();
			if(!currFiles.contains(storedFileName)) {
				file.delete(); //파일 삭제
				log.info(">>>> delete files >> {}", storedFileName);
			}
		}
		
		log.info(">>>>> FileSweeper Running End~!! : {}", LocalDateTime.now());
	}
}
