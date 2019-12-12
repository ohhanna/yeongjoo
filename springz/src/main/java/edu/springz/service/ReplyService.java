package edu.springz.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.springz.domain.Criteria;
import edu.springz.domain.ReplyPageDTO;
import edu.springz.domain.ReplyVO;

public interface ReplyService {
	
	public ReplyPageDTO getListPage(Criteria cri, Long no);
	
	public List<ReplyVO> getList(@Param("cri") Criteria cri, @Param("no") Long no); //댓글 전체조회
	
	public int modify(ReplyVO rvo);	//댓글 수정
	
	public int remove(Long rno);	 //댓글 삭제
	
	public ReplyVO get(Long rno);	 //댓글 하나 조회
	
	public int register(ReplyVO vo);	 //댓글삽입

}
