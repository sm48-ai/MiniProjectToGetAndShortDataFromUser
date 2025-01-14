package com.satish.serviceimpl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satish.dto.DashboardResponse;
import com.satish.entities.Counsellor;
import com.satish.entities.Enquiry;
import com.satish.repo.CounsellorRepo;
import com.satish.repo.EnquiryRepo;
import com.satish.service.CounsellorService;

@Service
public class CounsellorServiceImpl implements CounsellorService {
	@Autowired
	private CounsellorRepo counsellorRepo;
	@Autowired
	private EnquiryRepo enqRepo;

	@Override
	public boolean register(Counsellor counsellor) {
		Counsellor savedCounsellor=counsellorRepo.save(counsellor);
		if(null !=savedCounsellor.getCounsellorId()) {
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	public Counsellor findByEmail(String email) {
		Counsellor counsellor= counsellorRepo.findByEmail(email);
		return counsellor;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		Counsellor counsellor=counsellorRepo.findByEmailAndPwd(email, pwd);
		return counsellor;
	}
	

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {
		DashboardResponse response=new DashboardResponse();
		
		List<Enquiry> listEnq = enqRepo.getEnquiriesByCounsellorId(counsellorId);
		int totalEnq=listEnq.size();
		int enrolledEnq=listEnq.stream()
				.filter(e -> e.getEnqStatus()
				.equals("Enrolled"))
				.collect(Collectors.toList())
				.size();
		int lostEnq=listEnq.stream()
				.filter(e -> e.getEnqStatus()
				.equals("lost"))
				.collect(Collectors.toList())
				.size();
		int openEnq=listEnq.stream()
				.filter(e -> e.getEnqStatus()
				.equals("open"))
				.collect(Collectors.toList())
				.size();
		response.setTotalEnqs(totalEnq);
		response.setEnrolledEnqs(enrolledEnq);
		response.setOpenEnqs(openEnq);
		response.setLostEnqs(lostEnq);
		return response;
	}

}
