package edu.springz.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.springz.domain.Criteria;
import edu.springz.domain.ReplyPageDTO;
import edu.springz.domain.ReplyVO;
import edu.springz.service.ReplyService;
import lombok.AllArgsConstructor;
import lombok.experimental.PackagePrivate;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {

	private ReplyService service;
	
	//댓글 달기
	@PostMapping(value = "new", consumes = "application/json"				// 전달받는 객체
							  , produces = { MediaType.TEXT_PLAIN_VALUE })	// 전달하는 객체..?
	// ResponseEntity : 응답할 때 메세지와 상태코드값 같이 보낼 수 있음
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		// JSON 데이터를 RVO 객체로 변환할 거라 @RequestBody 사용
		log.info("ReplyVO : " + vo);
		
		int insertCount = service.register(vo);
		
		log.info("Reply INSERT COUNT " + insertCount);
		// result가 1이면(성공) : ResponseEntity로 success 와 OK 보냄
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	// 댓글 전체 조회
    // page와 {page} 차이 알아야함
    @GetMapping(value = "pages/{no}/{page}", produces = { MediaType.APPLICATION_XML_VALUE,
                          								  MediaType.APPLICATION_JSON_UTF8_VALUE } )
    											// URL의 일부를 파라미터로 사용하는 PathVariable
    public ResponseEntity<ReplyPageDTO> getList( @PathVariable("page") int page, @PathVariable("no") Long no ) {
        log.info("Controller - getList");
        // 댓글의 페이지수를 받아서 10개씩 페이징할 것
        Criteria cri = new Criteria(page, 3);
        log.info("Controller - getList - cri : " + cri);
        // service에서 받아온 리스트의 값과 OK를 함께 넘김
        return new ResponseEntity<>(service.getListPage(cri, no), HttpStatus.OK);
    }
	
	//댓글 조회
	@GetMapping(value = "{rno}", produces = { MediaType.APPLICATION_XML_VALUE,
											  MediaType.APPLICATION_JSON_UTF8_VALUE} )		
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		return new ResponseEntity<>(service.get(rno),HttpStatus.OK);
		
	}
	
	//댓글삭제
	@PreAuthorize("principal.username == #rvo.replyer")
	@DeleteMapping(value = "{rno}", produces = { MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@RequestBody ReplyVO rvo, @PathVariable("rno") Long rno){
		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	//댓글수정
	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH },
					value = "{rno}",
					consumes = "application/json",
					produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> modify( @RequestBody ReplyVO vo, @PathVariable("rno") Long rno){
		vo.setRno(rno);
		return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
}
