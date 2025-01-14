package com.satish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.satish.dto.DashboardResponse;
import com.satish.entities.Counsellor;
import com.satish.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	@Autowired
	private CounsellorService counsellorService;

	@GetMapping("/")
	public String index(Model model) {
		Counsellor cobj = new Counsellor();
		model.addAttribute("counsellor", cobj);
		return "index";
	}

	@PostMapping("/login")
	public String handleLoginBtn(Counsellor counsellor, HttpServletRequest request, Model model) {

		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());

		if (c == null) {
			model.addAttribute("emsg", "Invalid Credential");
			return "index";
		} else {
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellorId());

			return "redirect:/dashboard";
		}

	}

	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {

		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		DashboardResponse dbresObj = counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardInfo", dbresObj);

		return "dashboard";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		Counsellor cobj = new Counsellor();
		model.addAttribute("counsellor", cobj);
		return "register";
	}

	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor, Model model) {

		boolean isRegistered = counsellorService.register(counsellor);
		if (isRegistered) {
			model.addAttribute("smsg", "Registration successfull");
		} else {
			model.addAttribute("emsg", "Registration failed");
		}

		return "register";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		session.invalidate();
		return "redirect:/";
	}

}
