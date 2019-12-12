package edu.springz.mapper;

import edu.springz.domain.MemberVO;

public interface MemberMapper {
	
	public MemberVO read(String userid);
}
