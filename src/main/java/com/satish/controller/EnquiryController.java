package com.satish.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.satish.dto.DashboardResponse;
import com.satish.dto.ViewEnqsFilterRequest;
import com.satish.entities.Enquiry;
import com.satish.service.CounsellorService;
import com.satish.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
	private EnquiryService enqService;
	@PostMapping("/filter-enqs")
	public String filterEnquiries(ViewEnqsFilterRequest viewEnqsFilterRequest, HttpServletRequest req, Model model) {
		HttpSession session=req.getSession();
		Integer counsId=(Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqsList=enqService.getEnquiryWithFilter(viewEnqsFilterRequest, counsId);
		model.addAttribute("enquiries", enqsList);
		return "viewEnqsPage";
	}
	
	@GetMapping("/view-enquiries")
	public String getEnquries(HttpServletRequest request, Model model) {
		HttpSession session=request.getSession();
		Integer counId=(Integer) session.getAttribute("counsellorId");
		
		List<Enquiry> enqList=  enqService.getAllEnquiries(counId);
		model.addAttribute("enquiries", enqList);
		ViewEnqsFilterRequest filterReq=new ViewEnqsFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", filterReq);
		
		return "viewEnqsPage";
	}
		
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model  model) {
		Enquiry enqobj=new Enquiry();
		model.addAttribute("enq", enqobj);
		return "enquiryForm";
		
	}

	
	@PostMapping("/addEnq")
	public String hanldleAddEnquiry(@ModelAttribute("enq") Enquiry enq ,HttpServletRequest req,  Model model) throws Exception {
		
		HttpSession session=req.getSession(false);
		Integer counsellorId=(Integer) session.getAttribute("counsellorId");
		
		boolean isSaved=enqService.addEnquiry(enq, counsellorId);
		if(isSaved) {
			model.addAttribute("smsg", "Enquiry Added Successfully");
		}else {
			model.addAttribute("emsg", "failed to add the Enquiry");
		}
		return "enquiryForm";
	}

}
