package edu.springz.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.springz.domain.Criteria;
import edu.springz.domain.MemoAttachVO;
import edu.springz.domain.MemoVO;
import edu.springz.domain.PageDTO;
import edu.springz.service.MemoService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/memo/*")
@AllArgsConstructor
public class MemoController {
	private MemoService service;
	
	//파일 삭제 메서드 
	private void deleteFiles(List<MemoAttachVO> attachList) {
		
//			if(attachList == null || attachList.size() == 0) {
//				return;
//			}
		log.info("delete attach files");
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());
				Files.deleteIfExists(file); //원본파일 삭제
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());
					Files.delete(thumbNail); //썸네일파일 삭제
				}
			} catch (IOException e) {
				log.error("delete file error" + e.getMessage());
				e.printStackTrace();
			}
		});
	}
	
	@GetMapping( { "get", "modify"} )
	public void get(Model model, @RequestParam("no") Long no, @ModelAttribute("cri") Criteria cri) {
		log.info("Controller get/modify ");
		model.addAttribute("memo", service.get(no));
	}
	
	@PreAuthorize("principal.username == #memo.id")
	@PostMapping("modify")
	public String modify(MemoVO memo, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {
		log.info("Controller modify");
		if(service.modify(memo)) {
			rttr.addFlashAttribute("result", "success");
		}
		

//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
      
		return "redirect:/memo/list" + cri.getListLink();
		// redirect할 때 파라미터를 계속 추가하지 않고 미리 만들어둔 메소드를 통해 URL로 보냄
		
//		return "redirect:/memo/list";
	}
	
//	@PostMapping("remove")
//	public String remove(@RequestParam("no") Long no, RedirectAttributes rttr) {
//		log.info("Controller remove");
//		if(service.remove(no)) {
//			rttr.addFlashAttribute("result", "success");
//		}
//		return "redirect:/memo/list";
//	}
	
	@PreAuthorize("principal.username == #id")
	@GetMapping("remove")
	public String remove(String id, @RequestParam("no") Long no, RedirectAttributes rttr,  @ModelAttribute("cri") Criteria cri) {
		log.info("Controller remove");
		
		//첨부파일이 있는경우 파일 삭제 메서드 호출
		List<MemoAttachVO> attachList = service.getAttachList(no);
	    if(service.remove(no)) {
	         if(attachList != null || attachList.size() > 0 ) {
	            deleteFiles(attachList);
	         }
	         rttr.addFlashAttribute("result", "success");
	    }
		
		return "redirect:/memo/list" + cri.getListLink();
//		return "redirect:/board/list";
	}
	
	@GetMapping("read")
	public void read(Model model, @RequestParam("no") Long no) {
		log.info("Controller read ");
		model.addAttribute("read", service.get(no));
	}
	
	@PostMapping("register")
	@PreAuthorize("isAuthenticated()")
	public String register(MemoVO memo, RedirectAttributes rttr) {
		log.info("Controller register()");
		
		//첨부파일 있을때 처리
		if(memo.getAttachList() != null) {
			memo.getAttachList().forEach(attach -> log.info(attach));
		}
		
		service.register(memo);
		rttr.addFlashAttribute("result", memo.getNo()); // 등록된 게시물의 번호를 result에 저장해서 list 페이지로 redirect
		
		return "redirect:/memo/list";
	}
	
	
	@GetMapping("register")
	@PreAuthorize("isAuthenticated()")
	public void register(Model model) {
		log.info("Controller list ");
//		model.addAttribute("list", service.getList());
	}
	
	@GetMapping("list")
	public void list(Criteria cri, Model model) {
		log.info("Controller list ");

		model.addAttribute("memo", service.getList(cri));
		model.addAttribute("pageMaker", new PageDTO(cri, service.getTotal(cri)));
		
	}
	
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<MemoAttachVO>> getAttachList(Long no) {
		log.info("getAttachList " + no);
		return new ResponseEntity<>(service.getAttachList(no), HttpStatus.OK);
	}
	
}
