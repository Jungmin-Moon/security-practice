package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entities.CustomUserDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		/*
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		String redirectUrl = request.getContextPath();
		
		for (GrantedAuthority grantedAuthority : authorities) {
			String role = grantedAuthority.getAuthority();
			
			if (role.equals("USER")) {
				redirectUrl = "/profile";
			}
		}
		
		System.out.println(authentication.getAuthorities());
		*/
		
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		
		String redirectURL = request.getContextPath();
		
		if (userDetails.hasRole("USER")) {
			redirectURL = "/profile";
		}
		
		if (userDetails.hasRole("ADMIN")) {
			redirectURL = "/admin";
		}
		
		response.sendRedirect(redirectURL);
	}
}
