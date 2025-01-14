package com.satish.service;

import java.util.List;

import com.satish.dto.ViewEnqsFilterRequest;
import com.satish.entities.Enquiry;

public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq , Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	
	public List<Enquiry> getEnquiryWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer enqId);

}
