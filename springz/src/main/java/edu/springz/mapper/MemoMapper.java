package edu.springz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import edu.springz.domain.Criteria;
import edu.springz.domain.MemoVO;

public interface MemoMapper {
	
	public void updateReplyCnt(@Param("no") Long no, @Param("amount") int amount); //댓글 총 갯수
	
	public int getTotalCount(Criteria cri); //총페이지
	
	public List<MemoVO> getListWithPaging(Criteria cri);
	
	public int update(MemoVO memo);	//수정 - 성공하면 1이니깐 int
	
	public int delete(Long no); //삭제
//	
	public MemoVO read(Long no); //읽기
//	
	public void insertSelectKey(MemoVO memo); 
//	
	public void insert(MemoVO memo); //게시글 삽입
	
	public List<MemoVO> getList();//전체게시물가져오기
}
