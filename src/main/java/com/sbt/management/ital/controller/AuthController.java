package com.sbt.management.ital.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbt.management.ital.auth.AuthHelper;
import com.sbt.management.ital.auth.IdToken;
import com.sbt.management.ital.auth.TokenResponse;
import com.sbt.management.ital.service.outlook.OutlookService;
import com.sbt.management.ital.service.outlook.OutlookServiceBuilder;
import com.sbt.management.ital.service.outlook.User;

@Controller
public class AuthController {
	Logger LOG = Logger.getLogger(AuthController.class.getName());

	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorize(Model model, @RequestParam("code") String code, @RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state, HttpServletRequest request) {
		// Get the expected state value from the session
		HttpSession session = request.getSession();
		UUID expectedState = (UUID) session.getAttribute("expected_state");
		UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

		if (state.equals(expectedState)) {
			session.setAttribute("authCode", code);
			session.setAttribute("idToken", idToken);

			IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());

			if (idTokenObj != null) {
				TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
				// Get user info
				OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(),
						null);
				User user;
				try {
					user = outlookService.getCurrentUser().execute().body();
					session.setAttribute("userEmail", user.getMail());
				} catch (IOException e) {
					session.setAttribute("error", e.getMessage());
				}
				session.setAttribute("tokens", tokenResponse);
				session.setAttribute("userConnected", true);
				session.setAttribute("userName", idTokenObj.getName());
				session.setAttribute("userTenantId", idTokenObj.getTenantId());
			} else {
				session.setAttribute("error", "ID token failed validation.");
			}
		} else {
			session.setAttribute("error", "Unexpected state returned from authority.");
		}
		return "main";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/main";
	}
}