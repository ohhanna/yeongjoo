package edu.springz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.springz.domain.Criteria;
import edu.springz.domain.MemoAttachVO;
import edu.springz.domain.MemoVO;
import edu.springz.mapper.MemoAttachMapper;
import edu.springz.mapper.MemoMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor //생성자 어노테이션
public class MemoServiceImpl implements MemoService {
	
	private MemoMapper mapper;
	private MemoAttachMapper attachMapper;
	
	@Transactional
	@Override
	public void register(MemoVO memo) {
		log.info("register " + memo);
		
		mapper.insertSelectKey(memo);
		
		List<MemoAttachVO> attachList = memo.getAttachList();
		//첨부파일 없을 때 처리
		if(attachList == null || attachList.size() <= 0) {
			return;
		}
		//첨부파일 있을 때 처리
		memo.getAttachList().forEach(attach ->{
			attach.setNo(memo.getNo());
			attachMapper.insert(attach);
		});
	}
	@Transactional
	@Override
	public boolean modify(MemoVO memo) {
		
		//기존 첨부파일 삭제
		attachMapper.deleteAll(memo.getNo());
		//업데이트 처리
		boolean modifyResult = mapper.update(memo) == 1;
		
		//업데이트에 성공한경우 첨부 파일이 있으면
		if(modifyResult && memo.getAttachList() != null && memo.getAttachList().size() > 0) {
			memo.getAttachList().forEach(attach -> {
				attach.setNo(memo.getNo());	
				attachMapper.insert(attach);	//파일등록처리
			});
		}
		return modifyResult;
		
	}

	@Transactional
	@Override
	public boolean remove(Long no) {
		attachMapper.deleteAll(no);
		
		return mapper.delete(no) == 1;
		
//		int cnt = mapper.delete(bno);
//		log.info("remove Count : " + cnt);
//	    if(cnt == 1) {
//			return true;
//		}else {
//			return false;
//		}
	}

	@Override
	public MemoVO get(Long no) {
		MemoVO mvo = mapper.read(no);
		log.info("get");
		return mvo;
	}

	@Override
	public List<MemoVO> getList() {
		log.info("getList");
		return mapper.getList();
	}

	@Override
	public List<MemoVO> getList(Criteria cri) {
		log.info("getList......... : ");
		
		return mapper.getListWithPaging(cri);	
	}

	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<MemoAttachVO> getAttachList(Long no) {
		
		return attachMapper.findByBno(no);
	}
	
}
