package com.example.SortBoxs.entitys;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Token {
    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    private String token;

    private boolean revoked;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public static TokenBuilder builder() {
	    return new TokenBuilder();
	  }

	  public static class TokenBuilder {
	    private Long id;
	    private String tokenValue;
	    private boolean revoked;
	    private boolean expired;
	    private User user;

	    public TokenBuilder id(Long id) {
	      this.id = id;
	      return this;
	    }

	    public TokenBuilder token(String tokenValue) {
	      this.tokenValue = tokenValue;
	      return this;
	    }

	    public TokenBuilder revoked(boolean revoked) {
	      this.revoked = revoked;
	      return this;
	    }

	    public TokenBuilder expired(boolean expired) {
	      this.expired = expired;
	      return this;
	    }

	    public TokenBuilder user(User user) {
	      this.user = user;
	      return this;
	    }

	    public Token build() {
	      Token token = new Token();
	      token.setId(id);
	      token.setToken(tokenValue);
	      token.setRevoked(revoked);
	      token.setExpired(expired);
	      token.setUser(user);
	      return token;
	    }
	  }
    
    
}
