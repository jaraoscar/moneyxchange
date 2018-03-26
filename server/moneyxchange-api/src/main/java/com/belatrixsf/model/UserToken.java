package com.belatrixsf.model;

/**
 * The UserToken entity.
 */
public class UserToken {

	private String token;
	
	public UserToken() {
		super();
	}

	public UserToken(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}