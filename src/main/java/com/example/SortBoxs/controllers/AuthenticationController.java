package com.example.SortBoxs.controllers;

import lombok.RequiredArgsConstructor;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SortBoxs.Requests.AuthenticationRequest;
import com.example.SortBoxs.Requests.AuthenticationResponse;
import com.example.SortBoxs.Requests.RegisterRequest;
import com.example.SortBoxs.Requests.UpdatePasswordRequest;
import com.example.SortBoxs.entitys.Role;
import com.example.SortBoxs.entitys.User;
import com.example.SortBoxs.exception.EmailAlreadyExistsException;
import com.example.SortBoxs.exception.PasswordDontMatchException;
import com.example.SortBoxs.services.AuthenticationService;
import com.example.SortBoxs.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	private AuthenticationService authenticationService;

	private UserService userService;

	@Autowired
	public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
		this.authenticationService = authenticationService;
		this.userService = userService;
	}

	@PostMapping("register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
		try {
			authenticationService.registerUser(request);
			String email = request.getEmail();
			return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully  " + email);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Somethings isn't right");
		}
	}

	@PostMapping("/verify")
	public String VerifyOtpndGenPassword(@RequestParam String email, @RequestParam String password) throws Exception {

		String response = userService.updatePassword(email, password);

		return response;

	}

	@PostMapping("authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
		
		AuthenticationResponse responseToken = authenticationService.authenticate(request);
//		User retrivedUser = userService.findUserByEmail(request.email());
//		String firstname = retrivedUser.getFirstName();
//		Long userId = retrivedUser.getId();
//		Role role = retrivedUser.getRole();
//
//		String roleDescription = null;
//		switch (role) {
//		case ROLE_ADMIN:
//			roleDescription = "Admin";
//			break;
//		case ROLE_USER:
//			roleDescription = "User";
//			break;
//		// Add more cases for other roles if needed
//
//		default:
//			roleDescription = "Unknown";
//			break;
//		}
//	                        logger.info("role of the logged person is " + roleDescription);
//		 AuthenticationResponse updatedResponse = new AuthenticationResponse(
//			        responseToken.accessToken(),
//			        responseToken.refreshToken(),
//			        request.email(),
//			        roleDescription,
//			        firstname,
//			        userId  // Set userId in the response
//			    );
		return ResponseEntity.ok(responseToken);
	}

	@PostMapping("enable-user/{token}")
	public ResponseEntity<String> enableUser(@PathVariable String token) {
		try {
			authenticationService.enableUser(token);
			// If the enableUser method succeeds, perform the redirect

			return ResponseEntity.status(HttpStatus.FOUND).header("Location", "http://localhost:9090/login").body(null);
		} catch (ExpiredJwtException e) { // ExpiredJwtException is a custom exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Link has expired. Please request a new one.");
		} catch (UsernameNotFoundException e) { // UserNotFoundException is a custom exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not found !!");
		} catch (Exception e) { // For any other unhandled exceptions, return a generic error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to enable user.");
		}
	}

//    @PostMapping("refresh-token")
//    public ResponseEntity<AuthenticationResponse> refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException, java.io.IOException {
//        AuthenticationResponse responseToken = authenticationService.refreshToken(request, response);
//        return ResponseEntity.ok(responseToken);
//    }

//    @PostMappingb("forgot-password")
//    public ResponseEntity<String> sendResetPasswordRequest(
//            @Valid @RequestBody EmailRequest request
//    ) {
//        try {
//        	
//        	
//            authenticationService.sendResetPasswordRequestToUser(request.email());
//            
//            
//            
//            return ResponseEntity.ok("Reset password link sent to user with email " + request.email());
//        } catch (UsernameNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Email does not exist");
//        } catch (MailSendException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while sending reset password link");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An unexpected error occurred while processing the request.");
//        }
//    }

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok("User deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error deleting user.");
		}
	}


}