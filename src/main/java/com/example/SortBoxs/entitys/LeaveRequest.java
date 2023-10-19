package com.example.SortBoxs.entitys;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class LeaveRequest {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long userId;
	    private String reportingManager;
	    private String leaveType;
	    private java.util.Date fromDate;
	    private Date toDate;
	    private String reasonForLeave;
	    private Date createdAt;
	    private String createdBy;
	    private Date updatedAt;
	    private String updatedBy;
	
}
