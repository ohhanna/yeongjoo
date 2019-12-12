package edu.springz.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.springz.domain.MemoVO;
import edu.springz.mapper.MemoMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemoMapperTests {
	
	@Setter(onMethod_ = @Autowired)
    private MemoMapper mapper;
	
	@Test
	public void testUpdate() {
		MemoVO board = new MemoVO();
		board.setNo(5L);
		board.setTitle("이이이잉");
		board.setContent("새로 작성하는 내용 s");
		board.setId("newbie ddddddd");
		
		log.info("testUpdate Count : " + mapper.update(board));
		testGetList();
	}
	
//	@Test
	public void testDelete() {
	    log.info("testDelete Count : " + mapper.delete(4L));
	    mapper.getList();
	}
	
//	@Test
	public void testRead() {
		MemoVO bvo =mapper.read(7L);
		log.info(bvo);
	}
	
//	@Test
	public void testInsertSelectKey() {
		MemoVO board = new MemoVO();
		board.setTitle("새로 작성하는 글 s");
		board.setContent("새로 작성하는 내용 s");
		board.setId("newbie s");
		
		mapper.insertSelectKey(board);
		
		log.info(board);
	}
	
//	@Test
	public void testInsert() {
		MemoVO board = new MemoVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setId("newbie");
		
		mapper.insert(board);
		
		log.info(board);
	}
	
//	@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}

}