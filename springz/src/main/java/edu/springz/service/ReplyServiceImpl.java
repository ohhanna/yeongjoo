package edu.springz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.springz.domain.Criteria;
import edu.springz.domain.ReplyPageDTO;
import edu.springz.domain.ReplyVO;
import edu.springz.mapper.MemoMapper;
import edu.springz.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor //객체 넘겨받는다
public class ReplyServiceImpl implements ReplyService {
	private ReplyMapper mapper;
	private	MemoMapper memoMapper;

	@Override
	public List<ReplyVO> getList(Criteria cri, Long no) {
		return mapper.getListWithPaging(cri, no);
	}

	@Override
	public int modify(ReplyVO rvo) {
		return mapper.update(rvo);
	}

	@Transactional
	@Override
	public int remove(Long rno) {
		ReplyVO vo = mapper.read(rno);
		
		memoMapper.updateReplyCnt(vo.getNo(), -1);
		
		return mapper.delete(rno);
	}

	@Override
	public ReplyVO get(Long rno) {
		return mapper.read(rno);
	}

	@Transactional
	@Override
	public int register(ReplyVO vo) {
		memoMapper.updateReplyCnt(vo.getNo(), 1);
		return mapper.insert(vo);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long no) {

		return new ReplyPageDTO(mapper.getCountByBno(no), mapper.getListWithPaging(cri, no));
	}
	
	
	
	
}
