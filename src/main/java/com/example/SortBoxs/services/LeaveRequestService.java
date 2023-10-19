package com.example.SortBoxs.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SortBoxs.entitys.LeaveRequest;
import com.example.SortBoxs.repository.LeaveRepository;

@Service
public class LeaveRequestService {
	private final LeaveRepository leaveRequestRepository;

    @Autowired
    public LeaveRequestService(LeaveRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequest.setCreatedAt(new Date());
        return leaveRequestRepository.save(leaveRequest);
    }

	
}
