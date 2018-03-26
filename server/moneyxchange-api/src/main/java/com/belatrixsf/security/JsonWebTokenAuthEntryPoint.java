package com.belatrixsf.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Used to handle all HTTP requests and return an Unauthorized response by default.
 */
@Component
public class JsonWebTokenAuthEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

}
