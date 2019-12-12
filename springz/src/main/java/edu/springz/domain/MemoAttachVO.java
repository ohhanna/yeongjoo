package edu.springz.domain;

import lombok.Data;

@Data
public class MemoAttachVO {
	
	private String uuid;		//UUID가 포함된 이름
	private String uploadPath;	//실페 파일이 업로드된 경로
	private String fileName;	//파일이름
	private boolean fileType;	//이미지 파일여부 있는지 없는지
	
	private Long no;

}
