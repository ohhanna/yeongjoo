package edu.springz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {
	
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("CommonController access denied : " + auth);
		model.addAttribute("msg", "Access Denied");
	}
	
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		log.info("error : " + error);
		log.info("logout : " + logout);
		if(error!=null) {
			model.addAttribute("error", "LOGIN ERROR CHECK YOUR ACCOUNT");
		}
		if(logout != null) {
			model.addAttribute("logout", "LOGOUT!!");
		}
		
	}
	
	@GetMapping("/customLogout")
	public void Logout() {
		// 로그아웃 페이지로 이동
	}
	@PostMapping("/customLogout")
	public void LogoutPost() {
		// 로그아웃 설정
	}
	

}