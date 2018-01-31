package com.sbt.management.ital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbt.management.ital.auth.AuthHelper;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class WebController {
	Logger LOG = Logger.getLogger(WebController.class.getName());

	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request) {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);

		String loginUrl = AuthHelper.getLoginUrl(state, nonce);
		model.addAttribute("loginUrl", loginUrl);

		/*if (request.getSession().getAttribute("userConnected") != null)
			model.addAttribute(request.getSession().getAttribute("userConnected"));*/
		return "main";
	}

	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest request) {
		return index(model, request);
	}
}
