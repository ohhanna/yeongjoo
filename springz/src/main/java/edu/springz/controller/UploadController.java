package edu.springz.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.springz.domain.AttachFileDTO;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	//오늘 날짜로 년월일 폴더생성
	private String getFolder() {
		//현재 시점의 연\\월\\일 폴더 경로 문자열 생성 및 반환
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
	//이미지 파일 여부 확인
	private boolean checkImageType(File file) {
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			log.info("checkImageType : " + contentType);
			//시작값이 image이면
			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		FileTypeDetector를 사용하여 MIME형식을 검사하는 probeContentType의 기본 구현은 
//		OS에 따라 조금씩 차이를 보이며 때때로 실패할 수도 있으며 파일에 확장명이 없으면 오류가 발생할 수도 있다
		//ppt업로드가능
		
		return false;
		
	}
	
	//get방식으로 첨부파일을 업로드 할수 있는 화면을 처리하는 메서드
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	//post방식으로 첨부파일 업로드를 처리하는 메서드
	@PostMapping("/uploadFormAction")
	public void uploadFormAction(MultipartFile[] uploadFile, Model model) {
		
		//사진을 저장할 위치
		String uploadFolder = "C:\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			
			log.info("-----");
			log.info("upload File Name : " + multipartFile.getOriginalFilename());
			log.info("upload File Size : " + multipartFile.getSize());
			
			//지정한 위치에 지정한 이름으로 저장한다
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}//END catch
		}//END for
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("uploadAjax");
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/uploadAjaxAction")
	@ResponseBody //@RestController를 쓸수 없어서
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		log.info("uploadAjaxPost");	//pptx나 excel은 json으로 받아서 실패가 뜨나 저장은된다 이오류는 잡아줘야한다
		
		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();
		String uploadFolder = "C:\\upload"; //사진을 저장할 위치
		
		//폴더 만든다
		File uploadPath = new File(uploadFolder, getFolder());
		log.info("upload path : " + uploadPath);
		
		//연\\월\\일 폴더가 없으면 생성
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			
			//객체만든다
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			log.info("-----");
			log.info("upload File Name : " + multipartFile.getOriginalFilename());
			log.info("upload File Size : " + multipartFile.getSize());
			
			//파일명 다르게할려고 UUID값 해준다
			UUID uuid = UUID.randomUUID();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			//파일이름 넣는다									
			attachDTO.setFileName(uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1)); //익스플로어 파일경로
			
			//중복안되게 uuid 붙여서 이름 변경
			uploadFileName = uuid.toString() + "_" + uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
//			File saveFile = new File(uploadFolder, uploadFileName);
			File saveFile = new File(uploadPath, uploadFileName);
			
			try {
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(getFolder());
				//이미지 파일 체크
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					
					thumbnail.close();
				}
				//list에 AttachFileDTO 추가
				list.add(attachDTO);
			} catch (IllegalStateException | IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
		}//END for
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//파일 섬네일 보여주기
	@GetMapping("/display")
	@ResponseBody
	//파일을 받아야해서 바이트로 써준다
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("filename : " + fileName);
		
		File file = new File("c:\\upload\\" + fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//파일다운로드 처리
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	//HTTP헤더 메시지 중에서 디바이스의 정보를 알 수 있는 헤더는   User-Agent 기존의 다운로드 파일은 User-Agent정보를 파라미터로 수집한다
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		
		log.info("download file : " + fileName);
		
		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
		log.info("resource : " + resource);
		//resource가 없으면 상태코드 404반환
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		//UUID 제거
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		log.info(resourceOriginalName);
		//HttpHeaders 객체를 이용해 다운로드시 파일의 이름을 처리하도록 합니다.
		HttpHeaders headers = new HttpHeaders();
		
		try {
			String downloadName = null;
			if(userAgent.contains("trident")) {	//IE의 경우
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replace("\\+", " ");
			}else if(userAgent.contains("Edge")){	//Edge의 경우
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
				log.info("Edge name : " + downloadName);
			}else {					//그 이이외의 경우
				log.info("Chrome browser");	
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
			}
			//다운로드시 저장되는 이름은 Content-Disposition을 이용해서 저장한다
			//파일이름에 대한 문자열처리는 파일 이름이 한글인 경우 저장할떄 깨지는 문제를 막기 위해서
			headers.add("Content-Disposition", "attachment; filename=" + downloadName);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		
	}
	
	//파일 삭제
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile : " + fileName);
		try {
			File file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			
			//이미지 파일이면 원본 이미지 삭제
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("largeFileName" + largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("deleted OK", HttpStatus.OK);
		
	}
	
	

}
