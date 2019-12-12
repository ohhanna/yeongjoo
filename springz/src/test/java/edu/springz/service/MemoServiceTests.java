package edu.springz.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.springz.domain.MemoVO;
import edu.springz.service.MemoService;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemoServiceTests {
	
	@Setter(onMethod_ = @Autowired)
	private MemoService service;
	
	@Test
	public void testRead() {
		MemoVO mvo =service.get(9L);
		log.info(mvo);
	}
	
//	@Test
	public void testDelete() {
	    log.info("testDelete Count : " + service.remove(7L));
	    testGetList();
	}
	
//	@Test
	public void testGetList() {
		service.getList().forEach(memo -> log.info(memo));
	}
	
//	@Test
	public void testUpdate() {
		MemoVO board = new MemoVO();
		board.setNo(7L);
		board.setTitle("이이이잉");
		board.setContent("새로 작성하는 내용 s");
		board.setId("newbie ddddddd");
		
		log.info("testUpdate Count : " + service.modify(board));
	}
	
//	@Test
	public void testRegister() {
		MemoVO board = new MemoVO();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setId("newbie");
		
		service.register(board);
		
		log.info("생성된 게시물 번호 : " + board.getNo() );
	}
	
//	@Test
	public void testExist() {
		
		assertNotNull(service);
		log.info("------------------");
		log.info(service);
		
	}
	
	
}
