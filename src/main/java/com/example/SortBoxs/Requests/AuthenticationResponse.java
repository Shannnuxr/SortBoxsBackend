package com.example.SortBoxs.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthenticationResponse {

	 @JsonProperty("access_token")
	    private String accessToken;

	    @JsonProperty("refresh_token")
	    private String refreshToken;

	    @JsonProperty("email")
	    private String email;

	    @JsonProperty("role")
	    private String role;

	    @JsonProperty("name")
	    private String name;

	    @JsonProperty("id")
	    private Long id;
}
