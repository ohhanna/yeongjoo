package edu.springz.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	
	private int startPage; 	//	시작페이지
	private int endPage;	//	끝페이지	
	private boolean prev, next;		//이전, 다음
	
	private int total;		//	총 페이지 갯수
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total ) {
		this.total = total;
		this.cri = cri;
		
		//끝페이지 계산 - 한 화면에 페이지 번호 10개를 출력한다고 가정
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		
		//시작 페이지 계산
		this.startPage = this.endPage - 9;
		
		//실제 끝 페이지 계산
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		//이전 페이지 계산
		this.prev = this.startPage > 1;
		
		//다음 페이지 계산
		this.next = this.endPage < realEnd;
	}

}
