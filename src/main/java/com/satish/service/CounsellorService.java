package com.satish.service;

import org.springframework.data.jpa.repository.Query;

import com.satish.dto.DashboardResponse;
import com.satish.entities.Counsellor;

public interface CounsellorService {
	
	public boolean register(Counsellor counsellor);
	
	public Counsellor login(String email, String pwd);
	
	public DashboardResponse getDashboardInfo(Integer counsellorId);
	
	public Counsellor findByEmail(String email);
	

}
