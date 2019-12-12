package edu.springz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.springz.domain.Criteria;
import edu.springz.domain.ReplyVO;

public interface ReplyMapper {
	
	public int getCountByBno(Long no); //총페이지
	
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("no") Long no); //댓글 전체조회
	
	public int update(ReplyVO rvo);	//댓글 수정
	
	public int delete(Long rno);	 //댓글 삭제
	
	public ReplyVO read(Long rno);	 //댓글 하나 조회
	
	public int insert(ReplyVO vo);	 //댓글삽입
	
	
}
