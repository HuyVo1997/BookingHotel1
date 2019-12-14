package com.bookinghotel.controller;

import com.bookinghotel.security.CustomAuthenticationSuccessHandler;
import com.bookinghotel.service.GooglePojo;
import com.bookinghotel.service.GoogleUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Controller
public class BaseController {
	
	@Autowired
	private GoogleUtils googleUtils;


	@RequestMapping("/login-google")
	public String loginGoogle(Principal principal, HttpServletRequest request) throws ClientProtocolException, IOException {
		String code = request.getParameter("code");
		if (code == null || code.isEmpty()) {
			return "redirect:/login?google=error";
		}
		String accessToken = googleUtils.getToken(code);
		GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
		UserDetails userDetail = googleUtils.buildUser(googlePojo);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
				userDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource()
				.buildDetails(request));
		SecurityContextHolder.getContext()
				.setAuthentication(authentication);
		String referer = request.getHeader("Referer");
		request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME,referer);
		return "redirect:/";
	}

}
