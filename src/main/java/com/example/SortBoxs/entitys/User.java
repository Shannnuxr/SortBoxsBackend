package com.example.SortBoxs.entitys;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_app", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class User {

	@Id
	@SequenceGenerator(name = "user_generator", sequenceName = "user_app_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@Column(name = "id")
	private Long id;

	@Column(length = 30)
	private String firstName;

	@Column(length = 30)
	private String lastName;

	@Column(length = 100)
	private String email;

	private String password;

	@Transient
	private String confirmPassword;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column
	private String otp;

	@Column
	private String mobileNumber;
	@Column
	private String workType;
	@Column
	private String numberSeries;

	@Column
	private String employeeNumber;
	@Column
	private java.util.Date joiningDate;

	@Column
	private String jobTitle;

	@Column
	private String SecondaryJobTitle;

	@Column
	private String timeType;
	@Column
	private String reportingManager;

	@Column
	private String legalEntity;
	@Column
	private String businessUnit;
	@Column
	private String department;
	@Column
	private String location;

	@Column
	private String probationPolicy;
	@Column
	private String noticePeriod;
	@Column
	private String inviteEmployeeToLogin;

	@Column
	private String enableOnboardingFlow;

	@Column
	private String leavePlan;

	@Column
	private String shift;

	@Column
	private String weeklyOff;

	@Column
	private String attendanceNumber;

	@Column
	private String holidaysList;

	@Column
	private String attendanceCaptureScheme;

	@Column
	private String attendanceTrackingPolicy;

	@Column
	private String overtimePolicy;

	@Column
	private String expensePolicy;

	@Column
	private String payGroup;

	@Column
	private double annualSalary;

	/**
	 * the user by default is not enable, until he activates his account.
	 */
	@Column(name = "enabled")
	private boolean enabled = true; // by default is false, until the user activates his account via email
									// verification.

	private boolean accountNonLocked = true; // by default is true, until the user is blocked by the admin.

	@OneToMany(mappedBy = "user")
	private List<Token> tokens;

	public static User of(String firstName, String lastName, String email, String password, String confirmPassword,
			Role role) {
		return new User();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public static class UserBuilder {
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		private String confirmPassword;
		private Role role;
		private boolean enabled;
		private boolean accountNonLocked;
		private String otp;

		public UserBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public UserBuilder otp(String otp) {
			this.otp = otp;
			return this;
		}

		public UserBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public UserBuilder email(String email) {
			this.email = email;
			return this;
		}

		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}

		public UserBuilder confirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
			return this;
		}

		public UserBuilder role(Role role) {
			this.role = role;
			return this;
		}

		public UserBuilder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public UserBuilder accountNonLocked(boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
			return this;
		}

		public User build() {
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPassword(password);
			user.setConfirmPassword(confirmPassword);
			user.setRole(role);
			user.setEnabled(enabled);
			user.setAccountNonLocked(accountNonLocked);

			return user;
		}

	}

}
