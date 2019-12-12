package edu.springz.security;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		
		log.warn("LOGIN SUCCESS");
		List<String> roleNames = new ArrayList<>();
		
		// 사용자 권한 리스트에 저장
		auth.getAuthorities().forEach(authority -> {
			roleNames.add(authority.getAuthority());
		});
		log.warn("ROLE NAMES : " + roleNames);
		if(roleNames.contains("ROLE_ADMIN")) {
			// ROLE_ADMIN이면 /sample/admin으로 리다이렉트
			response.sendRedirect("/sample/admin");
			return ;
		}
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/sample/member");
			return ;
		}
		response.sendRedirect("/");

	}

}
