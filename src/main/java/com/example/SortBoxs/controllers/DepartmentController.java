package com.example.SortBoxs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SortBoxs.entitys.Department;
import com.example.SortBoxs.repository.DepartmentRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class DepartmentController {

	
	@Autowired
	private DepartmentRepository deptRepo;
	
	@PostMapping("/addDept")
	public ResponseEntity<?> addDept(@RequestBody Department department){
		
		if (department!=null) {
			
			deptRepo.save(department);
		    return ResponseEntity.ok("department added successfully");
		}
		
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/getDept")
	public List<Department> getDepts(){
		return deptRepo.findAll();
	}
}
