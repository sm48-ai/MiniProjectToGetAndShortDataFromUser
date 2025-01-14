package com.satish.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satish.entities.Counsellor;
import com.satish.entities.Enquiry;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {
	
	// select * from counsellor_tbl where email=:email and pwd=:pwd
		public Counsellor findByEmailAndPwd(String email, String pwd);
		
		
	
		
		//select * from counsellor_tbl where email=:email;
		public Counsellor findByEmail(String email);
	

}
