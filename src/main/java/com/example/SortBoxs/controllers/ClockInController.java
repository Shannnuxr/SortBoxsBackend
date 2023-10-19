package com.example.SortBoxs.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;


import com.example.SortBoxs.entitys.ClockInRecord;
import com.example.SortBoxs.services.ClockInService;

@RestController
@RequestMapping("api/v1/auth")
public class ClockInController {
	
	@Autowired
	private ClockInService clockInRecordService;

//    @Autowired
//    public ClockInRecordController(ClockInRecordService clockInRecordService) {
//        this.clockInRecordService = clockInRecordService;
//    }
	   @PostMapping("/clockin")
	    public ResponseEntity<String> submitClockIn(@RequestBody ClockInRecord clockInRecord) {
	        ClockInRecord submittedClockIn = clockInRecordService.submitClockIn(clockInRecord);
	        if (submittedClockIn != null) {
	            return ResponseEntity.ok("Clock-in submitted successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit clock-in.");
	        }
	    }

	    @PostMapping("/clockout")
	    public ResponseEntity<String> submitClockOut(@RequestBody ClockInRecord clockOutRecord) {
	        ClockInRecord clockInRecord = clockInRecordService.findClockInRecordByUserId(clockOutRecord.getUserId());

	        if (clockInRecord != null) {
	            clockInRecord.setClockOutTime(new Date()); // Set clock-out time to the current time
	            // Calculate the total time
	            long totalMilliseconds = clockInRecord.getClockOutTime().getTime() - clockInRecord.getClockInTime().getTime();
	            long totalMinutes = totalMilliseconds / (60 * 1000);

	            clockInRecord.setTotalTimeInMinutes(totalMinutes);

	            clockInRecordService.updateClockInRecord(clockInRecord);

	            return ResponseEntity.ok("Clock-out submitted successfully. Total time: " + totalMinutes + " minutes.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clock-in record not found for the given user and date.");
	        }
	    }
	  

}
