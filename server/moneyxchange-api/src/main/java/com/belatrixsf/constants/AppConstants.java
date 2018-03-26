package com.belatrixsf.constants;

/**
 * Constants used across the application.
 */
public class AppConstants {

	/**
	 * The token validity in milliseconds (1h)
	 */
	public static final long TOKEN_VALIDITY = 1 * 60 * 60 * 1000;
	
	/**
	 * The token issuer.
	 * NOTE: The application name will be used but can be changed in a later time.
	 */
	public static final String TOKEN_ISSUER = "moneyxchange-api";
	
	/**
	 * The token signing key.
	 * NOTE: The application name will be used but can be changed in a later time.
	 */
	public static final String TOKEN_SIGNING_KEY = "moneyxchange-api";
	
	/**
	 * The prefix used to extract the token from the HTTP header
	 * NOTE: Include an empty space at the end since the token will be preceded after it.
	 */
	public static final String TOKEN_HTTP_HEADER_PREFIX = "Bearer ";
	
	/**
	 * The HTTP header name where the token can be extracted.
	 */
	public static final String TOKEN_HTTP_HEADER_NAME = "Authorization";
}
