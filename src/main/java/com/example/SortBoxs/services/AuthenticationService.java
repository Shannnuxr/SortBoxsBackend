package com.example.SortBoxs.services;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SortBoxs.Requests.AuthenticationRequest;
import com.example.SortBoxs.Requests.AuthenticationResponse;
import com.example.SortBoxs.Requests.RegisterRequest;
import com.example.SortBoxs.entitys.Role;
import com.example.SortBoxs.entitys.User;
import com.example.SortBoxs.exception.EmailAlreadyExistsException;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j

@Service
@AllArgsConstructor
public class AuthenticationService {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private TokenService tokenService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		try {
			// Attempts to authenticate the user with the provided email and password
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
			logger.info("spring authentication done");
		} catch (InternalAuthenticationServiceException e) {
			// If the authentication fails, throws an exception with a message indicating
			// invalid credentials
//            log.error("error while authenticating user with request {}", request);
			throw new BadCredentialsException("Invalid credentials");
		}

		// If the authentication is successful, retrieves the user from the database and
		// generates a JWT token
		User user = userService.validateCredentials(request.email(), request.password());

		logger.info("usser details validated");

		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user.getEmail());

		logger.info(accessToken);

		tokenService.revokeAllUserTokens(user);
		tokenService.saveUserToken(user, accessToken);
//		User retrivedUser = userService.findUserByEmail(request.email());
		String firstname = user.getFirstName();
		Long userId = user.getId();
		String role = user.getRole().toString();

		 AuthenticationResponse response = new AuthenticationResponse();
		    response.setAccessToken(accessToken);
		    response.setRefreshToken(refreshToken);
		    response.setEmail(request.email()); // Add the user's email if needed
		    response.setRole(role);
		    response.setName(firstname); // Assuming name corresponds to first name
		    response.setId(userId);

		    return response;

	}

//	public void setOwnPassword(String email, String password) {
//
//		userService.updatePassword(email, password);
//		
//		
//		
//
//	}

	private String generateRandomOTP() {
		// Implement logic to generate a random OTP (e.g., using a random number
		// generator)
		// For example, you can generate a 6-digit OTP as a string
		Random random = new Random();
		int otpInt = 100000 + random.nextInt(900000); // Generate a 6-digit random number
		return String.valueOf(otpInt);
	}

	public void registerUser(RegisterRequest registerRequest) throws Exception {

		if (userService.emailExists(registerRequest.getEmail())) {
			// log.error("Email already exists");
			throw new EmailAlreadyExistsException();
		}
		// to generate ootp for the regisration
		String otp = generateRandomOTP();

		User user = new User();
		user.setFirstName(registerRequest.getFirstName());
		user.setEmail(registerRequest.getEmail());
		user.setLastName(registerRequest.getLastName());
		user.setPassword(registerRequest.getPassword());
		user.setConfirmPassword(registerRequest.getConfirmPassword());
		user.setRole(registerRequest.getRole());
		user.setOtp(otp);
		user.setMobileNumber(registerRequest.getMobileNumber());
		user.setWorkType(registerRequest.getWorkType());
		user.setNumberSeries(registerRequest.getNumberSeries());
		user.setEmployeeNumber(registerRequest.getEmployeeNumber());
		user.setJoiningDate(registerRequest.getJoiningDate());
		user.setJobTitle(registerRequest.getJobTitle());
		user.setSecondaryJobTitle(registerRequest.getSecondaryJobTitle());
		user.setTimeType(registerRequest.getTimeType());
		user.setReportingManager(registerRequest.getReportingManager());
		user.setLegalEntity(registerRequest.getLegalEntity());
		user.setBusinessUnit(registerRequest.getBusinessunit());
		user.setDepartment(registerRequest.getDepartment());
		user.setLocation(registerRequest.getLocation());
		user.setProbationPolicy(registerRequest.getProbationPolicy());
		user.setNoticePeriod(registerRequest.getNoticePeriod());
		user.setInviteEmployeeToLogin(registerRequest.getInviteEmployeeToLogin());
		user.setEnableOnboardingFlow(registerRequest.getEnableOnboardingFlow());
		user.setLeavePlan(registerRequest.getLeavePlan());
		user.setShift(registerRequest.getShift());

		user.setWeeklyOff(registerRequest.getWeeklyOff());
		user.setAttendanceNumber(registerRequest.getAttendanceNumber());
		user.setHolidaysList(registerRequest.getHolidaysList());
		user.setAttendanceCaptureScheme(registerRequest.getAttandancecaptureScheme());
		user.setAttendanceTrackingPolicy(registerRequest.getAttandancecaptureScheme());
		user.setOvertimePolicy(registerRequest.getOvertimePolicy());
		user.setExpensePolicy(registerRequest.getExpencePolicy());
		user.setPayGroup(registerRequest.getPaygroup());
		user.setAnnualSalary(registerRequest.getAnnualSalary());

		User savedUser = userService.saveUser(user);
//		return ResponseEntity.ok(savedUser);

		var jwtToken = jwtService.generateTokenForEnableAccount(savedUser.getEmail());

		// activation link

		String activationLink = "https://sortbox-frontend.vercel.app/generateNewPassword";

		try {
			logger.info("Sending activation link to user {}", registerRequest.getEmail());
			emailService.sendActivationLink(registerRequest.getEmail(), registerRequest.getFirstName(), activationLink,
					otp);
		} catch (Exception e) {
			logger.error("Error while sending activation link to user {}", registerRequest.getEmail());
//			logger.info(
//					"If u didn't receive the email, due to the fact that we are in dev mode, we can pretend that the following link is sent : {}",
//					activationLink);
			throw new MailSendException(registerRequest.getEmail());
		}

		tokenService.saveUserToken(savedUser, jwtToken);
		logger.info("User successfully registered with request {}", registerRequest);

	}

	/**
	 * Generates a JWT token with an expiration date and sends a reset password
	 * email to the user containing a link to reset their password.
	 *
	 * @param email Email address of the user.
	 */
	public void sendResetPasswordRequestToUser(String email) {
		// If an account with the given email already exists, throws an exception
		var user = userService.findUserByEmail(email);

		var jwtToken = jwtService.generateTokenForResetPassword(user.getEmail());

		// create the link for the account activation & set the token as a param
		String resetPasswordLink = "http://localhost:5173/reset-password?token=" + jwtToken;

		// Send activation link.
		try {
			// log.info("Sending reset password link to user with email {}", email);
			emailService.sendResetPasswordRequestToUser(email, user.getFirstName(), resetPasswordLink);
		} catch (Exception e) {

			throw new MailSendException("Error while sending reset password link to user with email :" + email);
		}
		// log.info("Reset password link sent to user with email {}", email);
	}

//	public void upDatePassword(String token, String password, String passwordConfirm) throws Exception {
//		// retrieve the email from the token
//		String email = jwtService.extractUsername(token);
//		// update the password
//		userService.updatePassword(email, password, passwordConfirm);
//	}

	/**
	 * Enables a user given a JWT token.
	 *
	 * @param token JWT token containing the user email.
	 */
	public void enableUser(String token) {
		// retrieve the email from the token
		String email = jwtService.extractUsername(token);
		// enable the user
		userService.enableUser(email);
	}

	/**
	 * Checks if the password and password confirmation match.
	 *
	 * @param registerRequest The registration details of the user.
	 * @return True if the password and password confirmation match, false
	 *         otherwise.
	 */
//    public boolean isPasswordAndPasswordConfirmMatches(RegisterRequest registerRequest) {
//        // checks if the password and password confirm are the same
//        return registerRequest.password().equals(registerRequest.confirmPassword());
//    }

	/**
	 * Refreshes the JWT token.
	 *
	 * @param request  HTTP request.
	 * @param response HTTP response.
	 * @return The new JWT token.
	 * @throws IOException         If an error occurs while writing the response.
	 * @throws java.io.IOException
	 */
//	public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
//			throws IOException, java.io.IOException {
//		// initialize the result
//		AuthenticationResponse result = null;
//
//		// extract the token from the request header
//		final String authHeader = request.getHeader("Authorization");
//
//		// if the token is null or does not start with "Bearer ", return an error
//		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			response.getWriter().write("Missing or invalid Authorization header.");
////            log.error("Missing or invalid Authorization header.");
//		} else { // else, try to refresh the token
//			try {
//				// extract the refresh token
////                log.info("Refreshing token for request {}", request.getHeader("Authorization"));
//				final String refreshToken = authHeader.substring(7);
//
//				// extract the user email from the refresh token
//				var username = jwtService.extractUsername(refreshToken);
////                log.info("User email is {}", username);
//
//				// if the user email is not null, find the user in the database
//				if (username != null) {
//					// find the user in the database
//					var userDetails = userService.loadUserByUsername(username);
////                    log.info("User is {}", userDetails);
//
//					// if the user is not null and the refresh token is valid, generate a new access
//					// token
//					if (jwtService.isTokenValid(refreshToken, userDetails)) {
//						var accessToken = jwtService.generateAccessToken(userDetails.user()); // generate a new access
//																								// token
////                        log.info("Access token is {}", accessToken);
//						tokenService.revokeAllUserTokens(userDetails.user()); // revoke all user tokens
//						tokenService.saveUserToken(userDetails.user(), accessToken); // save the new access token
//
//						// set the result
//						result = new AuthenticationResponse(accessToken, refreshToken);
//					}
//				}
//			} catch (ExpiredJwtException ex) { // if the refresh token is expired, return an error
////                log.warn("refresh token expired: {}", ex.getMessage());
////                response.sendError(SC_UNAUTHORIZED, "refresh token expired");
//			} catch (MalformedJwtException e) { // if the refresh token is invalid, return an error
////                log.warn("refresh token expired: {}", e.getMessage());
////                response.sendError(SC_UNAUTHORIZED, "invalid refresh token.");
//			}
//		}

	// return the result
	// return result;
//	}

}
