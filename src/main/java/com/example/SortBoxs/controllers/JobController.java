package com.example.SortBoxs.controllers;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.SortBoxs.entitys.JobTitle;

import com.example.SortBoxs.repository.JobRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class JobController {

	
	@Autowired
	private JobRepository jobRepo;
	
	@PostMapping("/addJob")
	public String addJobDetails(@RequestBody JobTitle jobTitle) {
		
	JobTitle savedJobtitle =	jobRepo.save(jobTitle);
	
	if (savedJobtitle!=null) {
		
		return "job saved";
	}
		return "some things wrong";
	}
	@GetMapping("/getJob")
	public ResponseEntity<?> getJob(){
		
		          List<JobTitle> retrivedJob =  jobRepo.findAll();
		          
		          if (retrivedJob!=null) {
		        	  
		        	  
		        	  return ResponseEntity.ok(retrivedJob);
					
				}
		          
		          return ResponseEntity.badRequest().body("some things wrong");
		
		
	}
	
	
}



















