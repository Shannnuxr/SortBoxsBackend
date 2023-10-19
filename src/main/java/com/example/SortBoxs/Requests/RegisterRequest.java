package com.example.SortBoxs.Requests;

import org.hibernate.validator.constraints.Length;

import com.example.SortBoxs.entitys.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

	@Length(min = 3, max = 16, message = "first name length should be less than 16 and more than 3 ")
	private String firstName;

	@Length(min = 3, max = 16, message = "last name length should be less than 16 and more than 3 ")
	private String lastName;

	@Email(message = "Email should be valid")
	@NotNull(message = "Email shouldn't be null")
	@Length(min = 3, message = "email length should be more than 10 ")
	private String email;

	@NotNull(message = "Password shouldn't be null ")
	@Length(min = 8, max = 16, message = "password length should be more than 8 and less than 16")
	private String password;

	@NotNull
	private String confirmPassword;

	@NotNull
	private Role role;

	private String mobileNumber;

	private String workType;
	private String numberSeries;
	private String employeeNumber;
	private java.util.Date joiningDate;
	private String jobTitle;
	private String secondaryJobTitle;
	private String timeType;
	private String reportingManager;
	private String legalEntity;
	private String businessunit;
	private String department;
	private String location;
	private String probationPolicy;
	private String noticePeriod;
	private String inviteEmployeeToLogin;
	private String enableOnboardingFlow;
	private String leavePlan;
	private String shift;
	private String weeklyOff;
	private String attendanceNumber;
	private String holidaysList;
	private String attandancecaptureScheme;
	private String attandancetrakingPolicy;
	private String overtimePolicy;
	private String expencePolicy;
	private String paygroup;
	double annualSalary;

}
