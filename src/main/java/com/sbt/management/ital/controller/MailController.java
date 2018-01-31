package com.sbt.management.ital.controller;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sbt.management.ital.auth.AuthHelper;
import com.sbt.management.ital.auth.TokenResponse;
import com.sbt.management.ital.service.outlook.Message;
import com.sbt.management.ital.service.outlook.OutlookService;
import com.sbt.management.ital.service.outlook.OutlookServiceBuilder;
import com.sbt.management.ital.service.outlook.PagedResult;
import org.apache.commons.text.StringEscapeUtils;

@Controller
public class MailController {
	Logger LOG = Logger.getLogger(MailController.class.getName());

	@RequestMapping("/mail")
	public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");

		if (tokens == null) {
			LOG.log(Level.SEVERE, "Tokens are null");
			redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
			return "redirect:/main";
		}

		String tenantId = (String) session.getAttribute("userTenantId");

		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		String email = (String) session.getAttribute("userEmail");

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		String folder = "inbox";

		String sort = "receivedDateTime DESC";

		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";

		Integer maxResults = 10;

		try {
			PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute()
					.body();
			model.addAttribute("messages", messages.getValue());
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "IO Exception occured:", e);
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/main";
		}

		return "mail";
	}
}