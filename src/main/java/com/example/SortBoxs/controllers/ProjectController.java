package com.example.SortBoxs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SortBoxs.entitys.Project;
import com.example.SortBoxs.repository.ProjectRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class ProjectController {
	
	
	 @Autowired
	    private ProjectRepository projectRepository;

	    @PostMapping("/addProject")
	    public ResponseEntity<String> addProject(@RequestBody Project project) {
	        // Check if the required fields are present
	        if (project.getUserId() == null || project.getOrganization() == null ||
	            project.getProjectName() == null || project.getClientName() == null) {
	            return ResponseEntity.badRequest().body("Missing required fields");
	        }

	        // Save the project to the database
	        projectRepository.save(project);

	        return ResponseEntity.ok("Project added successfully");
	    }
	    @GetMapping("/getProject")
	    public ResponseEntity<List<Project>> getProject(@RequestParam("userId") Long userId) {
	        
	        
	        List<Project> projects = projectRepository.findByUserId(userId);

	        // Check if projects were found
	        if (projects.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        }

	        return ResponseEntity.ok(projects);
	    }


	

}
