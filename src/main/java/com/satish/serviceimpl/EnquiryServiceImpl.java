package com.satish.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.satish.dto.ViewEnqsFilterRequest;

import com.satish.entities.Counsellor;
import com.satish.entities.Enquiry;
import com.satish.repo.CounsellorRepo;
import com.satish.repo.EnquiryRepo;
import com.satish.service.EnquiryService;

import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService{
	@Autowired
	private EnquiryRepo enqRepo;
	
	@Autowired
	private CounsellorRepo counsellorRepo;
	
	public EnquiryServiceImpl(EnquiryRepo enqRepo, CounsellorRepo counsellorRepo) {
		this.enqRepo=enqRepo;
		this.counsellorRepo=counsellorRepo;
	}

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellor_id) throws Exception {
		
		Counsellor counsellor =counsellorRepo.findById(counsellor_id).orElse(null);
		if(counsellor==null) {
			throw new Exception("No counsellor Found");
		}
		enq.setCounsellor(counsellor);
		
		Enquiry savedEnq=enqRepo.save(enq);
		if(null !=savedEnq.getEnq_id()) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		List<Enquiry> counselloeEnquery=enqRepo.getEnquiriesByCounsellorId(counsellorId);
		return counselloeEnquery;
	}

	@Override
	public List<Enquiry> getEnquiryWithFilter(ViewEnqsFilterRequest filterReq, Integer counsellorId) {
		Enquiry enq=new Enquiry();
		if(StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}
		if(StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}
		if(StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}
		
		Counsellor c=counsellorRepo.findById(counsellorId).orElse(null);
		enq.setCounsellor(c);
		
		Example<Enquiry> exof=Example.of(enq);
		List<Enquiry> filterEnq=enqRepo.findAll(exof);
		
		return filterEnq;
	}

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		Enquiry enq=enqRepo.findById(enqId).orElse(null);
		return enq;
	}



}
