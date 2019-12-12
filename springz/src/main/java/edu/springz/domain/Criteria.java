package edu.springz.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Getter
@ToString
public class Criteria {
	
	private int pageNum;
	private int amount;

	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,3); //기본 페이지 번호는 1번, 갯수는 10개로 설정
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		// 사용자가 선택한 검색 조건을 잘라서 문자열 배열에 저장한다
		// 이 저장된 배열에 따라 쿼리가 동적으로 생성되어야 한다.
		return type == null? new String[] {} : type.split("");
	}
	
	// 파라미터를 계속 전달해야하는 상황에서
	// 여러 개의 파라미터들을 연결해서 URL형태로 만들어주는 기능을 함
	// 리다이렉트하거나, form 태그 사용하는 상황 많이 줄어든다.
	
	public String getListLink() {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
										 .queryParam("pageNum", this.pageNum)
										 .queryParam("amount", this.getAmount())
										 .queryParam("type",this.getType())
										 .queryParam("keyword", this.getKeyword());
		
		return builder.toUriString();
		
	}
}
