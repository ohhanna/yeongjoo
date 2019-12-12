package edu.springz.mapper;

import java.util.List;


import edu.springz.domain.MemoAttachVO;

public interface MemoAttachMapper {
	
	public void insert(MemoAttachVO vo);
	
	public void delete(String uuid);
	
	public List<MemoAttachVO> findByBno(Long no);
	
	public void deleteAll(Long bno);
	
	public List<MemoAttachVO> getOldFiles();

}
