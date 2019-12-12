package edu.springz.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.springz.domain.MemoAttachVO;
import edu.springz.mapper.MemoAttachMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
@AllArgsConstructor
public class FileCheckTask {
	
	private MemoAttachMapper attachMapper;
	private String getFolderYesterDay() {
	      // 어제 날짜 폴더의 문자열 반환
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	      String path = sdf.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
//	      Calendar cal = Calendar.getInstance();
//	      cal.add(Calendar.DATE, -1);
//	      String str = sdf.format(cal.getTime());
	      return path.replace("-", File.separator);
	}
	//자동으로 움직임
//	@Scheduled(cron = "0 * * * * *")
//	   public void checkFiles() throws Exception{
//	      Date now = new Date();
//	      log.warn("running~!~!!!! : " + now.toLocaleString());
//	      log.warn("--------------------------------------------------------");
//	      log.warn(getFolderYesterDay());
//	}
	
	//초 분 시 일 달 주 (년)
	@Scheduled(cron="0 0 14 * * *")	//12시에 시작
	public void checkFiles() throws Exception{
		log.warn("File Check Task run");
		log.warn(new Date());
		//데이터베이스에서 어제 사용된 파일의 목록 받아오기
		List<MemoAttachVO> fileList = attachMapper.getOldFiles();
		
		//해당 폴더의 파일 목록에서 데이터베이스에 없는 파일 찾아내기
		//tbl_attach 테이블의 데이터를 목록으로 반환
		List<Path> fileListPaths = fileList.stream().map(vo -> Paths.get("C:\\upload", vo.getUploadPath(), 
																		vo.getUuid() + "_" +vo.getFileName()))
																		.collect(Collectors.toList());
		
		//섬네일 이미지가 있는 경우 파일 목록에 추가
		fileList.stream().filter(vo -> vo.isFileType() == true).map(vo -> Paths.get("C:\\upload", vo.getUploadPath(), "s_"
																+ vo.getUuid() + "_" +vo.getFileName()))
																.forEach(p -> fileListPaths.add(p));
		log.warn("=============");
		
		fileListPaths.forEach(p -> log.warn(p));
		
		//어제 날짜 폴더 가져오기
		File targetDir = Paths.get("C:\\upload", getFolderYesterDay()).toFile();
		
		//데이터베이스에 없는 파일들 삭제하기
		//목록에 없는 삭제 대상 파일들을 배열에 저장
		File[] removeFiles = targetDir.listFiles(file -> fileListPaths.contains(file.toPath()) == false);
		
		log.warn("-----------");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
		
	}

}
