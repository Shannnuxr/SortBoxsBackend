package com.example.SortBoxs.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.SortBoxs.models.UserDetailsImpl;
import com.example.SortBoxs.services.JwtService;
import com.example.SortBoxs.services.TokenService;
import com.example.SortBoxs.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j

@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;

	// The Authorization header is in the form of Bearer <token>. The prefix Bearer
	// is removed to extract the token.
	private static final String AUTHORIZATION_HEADER = "Authorization";

	// The prefix Bearer is removed to extract the token.
	private static final String BEARER_PREFIX = "Bearer ";

	// The list of public endpoints that do not require authentication
	private final List<String> PUBLIC_ENDPOINTS = List.of("/api/v1/auth/register", "/api/v1/auth/refresh-token",
			"/api/v1/auth/enable-user", "/api/v1/authenticate", "/api/v1/auth/forgot-password",
			"/api/v1/auth/reset-password");

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException, java.io.IOException {

		if (PUBLIC_ENDPOINTS.contains(request.getServletPath())) {
			// log.info("skipping the filter for the following request url {} : ",
			    
			request.getServletPath();
			

			filterChain.doFilter(request, response);
		} else {

			final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
			final String jwt;
			final String username;

			// Check if Authorization header is missing or does not contain a valid JWT
			if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
				try {
					// Extract the JWT from the Authorization header
					jwt = authHeader.substring(7);

					// Extract the user email from the JWT and check if it is valid
					username = jwtService.extractUsername(jwt);

					if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
						// Retrieve the userDetails from the database
						UserDetailsImpl userDetails = userService.loadUserByUsername(username);

						var isTokenValid = tokenService.isTokenValid(jwt);
						// Check if the JWT is valid for the retrieved userDetails

						if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
							// create a new authentication token with the retrieved user
							UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());

							// Set the details of the authentication token
							authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

							// Set the authentication token in the SecurityContextHolder
							SecurityContextHolder.getContext().setAuthentication(authToken);
						}
					}
					// Proceed with the filter chain
					filterChain.doFilter(request, response);
				} catch (ExpiredJwtException ex) { // catch the exception if the token has expired
//					log.warn("JWT has expired: {}", ex.getMessage());
//					response.setStatus(SC_UNAUTHORIZED);
					response.getWriter().write("Access token expired");
				} catch (MalformedJwtException ex) { // catch the exception if the token is malformed
//					log.warn("JWT is malformed: {}", ex.getMessage());
//					response.setStatus(SC_UNAUTHORIZED);
					response.getWriter().write("JWT is malformed");
				} catch (SignatureException ex) { // catch the exception if the token is invalid
//					log.warn("JWT signature is invalid: {}", ex.getMessage());
//					response.setStatus(SC_UNAUTHORIZED);
					response.getWriter().write("JWT signature is invalid");
				} catch (UnsupportedJwtException exception) { // catch the exception if the token is unsupported
//					log.warn("JWT is unsupported: {}", exception.getMessage());
//					response.setStatus(SC_UNAUTHORIZED);
					response.getWriter().write("JWT is unsupported");
				} catch (Exception ex) { // catch the exception if any other error occurs
//					log.error("Failed to extract username from JWT: {}", ex.getMessage());
//					response.sendError(SC_INTERNAL_SERVER_ERROR, "Failed to extract username from JWT");
				}
			} else {
				// Authorization header is missing or does not contain a valid JWT
//				log.error(
//						"Authorization header is missing or does not contain a valid JWT for the following URL : {}  ",
//						request.getServletPath());

				logger.error(getServletContext());

//				log.error("Authorization header is missing or does not contain a valid JWT", request.getServletPath());
//
				filterChain.doFilter(request, response);
			}
		}
	}
}
