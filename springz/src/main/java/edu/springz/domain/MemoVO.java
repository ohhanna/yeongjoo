package edu.springz.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemoVO {
	private Long no;
	private String title;
	private String content;
	private String id;
	private Date regdate;
	private Date updateDate;
	
	private int replyCnt;
	
	private List<MemoAttachVO> attachList;	//등록 시 한번에 처리할 수 있도록 처리
}
