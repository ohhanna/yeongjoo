package edu.springz.service;

import java.util.List;


import edu.springz.domain.Criteria;
import edu.springz.domain.MemoAttachVO;
import edu.springz.domain.MemoVO;

public interface MemoService {
	
	public boolean modify(MemoVO memo);

	public boolean remove(Long no); 
	
	public MemoVO get(Long no); 
	
	public void register(MemoVO memo); 
	
	public List<MemoVO> getList();
	
	public List<MemoVO> getList(Criteria cri);	
	
	public int getTotal(Criteria cri);
	
	public List<MemoAttachVO> getAttachList(Long no);

}
