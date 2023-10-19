package com.example.SortBoxs.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SortBoxs.entitys.ClockInRecord;
import com.example.SortBoxs.repository.ClockInRepository;

@Service
public class ClockInService {

	    @Autowired
	   private  ClockInRepository clockInRecordRepository;

	    public ClockInRecord submitClockIn(ClockInRecord clockInRecord) {
	        clockInRecord.setClockInTime(new Date());
	        clockInRecord.setCreatedAt(new Date());
	        // You can set createdBy and other fields if needed
	        return clockInRecordRepository.save(clockInRecord);
	    }

	    public ClockInRecord findClockInRecordByUserId(Long userId) {
	        return clockInRecordRepository.findClockInRecordByUserId(userId);
	    }

	    public void submitClockOut(ClockInRecord clockOutRecord) {
	        ClockInRecord clockInRecord = findClockInRecordByUserId(clockOutRecord.getUserId());

	        if (clockInRecord != null) {
	            clockInRecord.setClockOutTime(new Date());

	            // Calculate the total time worked
	            long totalMilliseconds = clockInRecord.getClockOutTime().getTime() - clockInRecord.getClockInTime().getTime();
	            long totalMinutes = totalMilliseconds / (60 * 1000);
	            clockInRecord.setTotalTimeWorkedInMinutes(totalMinutes);

	            // Save the updated record
	            clockInRecordRepository.save(clockInRecord);
	        }
	    }

	    public ClockInRecord updateClockInRecord(ClockInRecord updatedRecord) {
	        // Find the existing record by ID or any other identifier
	        ClockInRecord existingRecord = clockInRecordRepository.findById(updatedRecord.getId()).orElse(null);

	        if (existingRecord != null) {
	            // Update the fields that need to be modified
	            existingRecord.setClockInTime(updatedRecord.getClockInTime());
	            existingRecord.setClockOutTime(updatedRecord.getClockOutTime());
	            existingRecord.setTotalTimeWorkedInMinutes(updatedRecord.getTotalTimeWorkedInMinutes());

	            // Save the updated record
	            return clockInRecordRepository.save(existingRecord);
	        } else {
	            // Handle the case when the record to be updated is not found
	            return null;
	        }
	    }
}
