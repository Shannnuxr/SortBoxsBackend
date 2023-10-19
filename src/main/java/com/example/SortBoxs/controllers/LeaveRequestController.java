package com.example.SortBoxs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SortBoxs.entitys.LeaveRequest;
import com.example.SortBoxs.services.LeaveRequestService;

@RestController
@RequestMapping("/api/v1/auth")
public class LeaveRequestController {
	
	  private final LeaveRequestService leaveRequestService;

	    @Autowired
	    public LeaveRequestController(LeaveRequestService leaveRequestService) {
	        this.leaveRequestService = leaveRequestService;
	    }

	    @PostMapping("/leaverequests")
	    public ResponseEntity<String> createLeaveRequest(@RequestBody LeaveRequest leaveRequest) {
	       // return leaveRequestService.createLeaveRequest(leaveRequest);
	        LeaveRequest createdLeaveRequest = leaveRequestService.createLeaveRequest(leaveRequest);
	        if (createdLeaveRequest != null) {
	            return ResponseEntity.ok("Leave request submitted successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit leave request.");
	        }
}
}
