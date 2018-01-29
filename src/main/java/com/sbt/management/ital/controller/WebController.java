package com.sbt.management.ital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbt.management.ital.AuthHelper;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class WebController {

	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request) {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);

		String loginUrl = AuthHelper.getLoginUrl(state, nonce);
		model.addAttribute("loginUrl", loginUrl);
		System.out.println(session.getAttribute("authCode"));

		return "main";
	}

	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest request) {
		System.out.println(request.getSession().getAttribute("authCode"));
		return index(model, request);
	}
}
