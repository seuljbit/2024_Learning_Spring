package com.ezen.spring.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.spring.domain.FileVO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component // 사용자 클래스를 빈으로 등록하는 어노테이션(메서드 단위 - 빈, 클래스 - 컴포넌트)
public class FileHandler {
	private final String UP_DIR = "D:\\_myProject\\_java\\_fileUpload"; // 저장 경로
	
	// 첨부파일을 주고 fileVO 리스트로 변환하여 리턴 + 저장
	public List<FileVO> uploadFiles(MultipartFile[] files) {
		List<FileVO> fileList = new ArrayList<>();
		
		// FileVO 생성 + 파일 저장 + 썸네일 저장
		// 날짜 별로 폴더 생성하여 업로드 파일 관리
		LocalDate date = LocalDate.now(); // 오늘 날짜 리턴 ex) 2024-11-01
		String today= date.toString();
		log.info(">>> date : {}", today);
		today = today.replace("-", File.separator); // 2024\11\01 -> window, 2024/11/01 -> mac

		File folders = new File(UP_DIR, today); // D:\\_myProject\\_java\\_fileUpload\\2024\\11\01
		
		// 폴더 생성(있으면 생성 x) : mkdir (1개만 생성), mkdirs(하위 폴더까지 생성)
		if(!folders.exists()) {
			folders.mkdirs();
		}
		
		// files를 가지고 fileList 생성
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSaveDir(today); // 2024\\11\\01
			fvo.setFileSize(file.getSize());
			
			// String fileName = file.getOriginalFilename();
			String fileName = file.getOriginalFilename().substring(
					file.getOriginalFilename().lastIndexOf(File.separator) + 1);
			log.info(fileName);
			fvo.setFileName(fileName);
			
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString();
			fvo.setUuid(uuidStr);
			log.info(">>> uuid : {}", uuidStr);
			
			// fvo 생성 > bno / file_type(File 객체로 전달해야 해서 저장 후 확인)
			// 디스크렝 저장 -> 저장할 객체(File)를 생성 > 저장
			String fullFileName = uuidStr + "_" + fileName;
			File storeFile = new File(folders, fullFileName);
			
			try {
				file.transferTo(storeFile); // 저장
				
				// 썸네일 저장 => 이미지만 가능 (파일 타입 검사 필요)
				if(isImageFile(storeFile)) {
					fvo.setFileType(1); // 이미지 파일만 1
					File thumbNail = new File(folders, uuidStr + "_th_" + fileName);
					Thumbnails.of(storeFile).size(100, 100).toFile(thumbNail);
				}
				
				
			} catch (Exception e) {
				log.info("파일 저장 error!");
				e.printStackTrace();
			}
			
			fileList.add(fvo); // for문 안에 있어야 함
		}
		
		return fileList;
	}
	
	private boolean isImageFile(File storeFile) throws IOException {
		String imageType = new Tika().detect(storeFile); // file의 내부 요소 type="image/jpge"
		
		return imageType.startsWith("image") ? true : false;
	}
	
}
