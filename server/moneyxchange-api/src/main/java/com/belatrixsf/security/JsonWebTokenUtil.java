package com.belatrixsf.security;

/**
 * Utility class for the user token
 */
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.belatrixsf.constants.AppConstants;
import com.belatrixsf.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JsonWebTokenUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new token
	 * @param user - The entity to be processed
	 * @return String
	 */
	public String generateToken(User user) {
		return this.doGenerateToken(user.getUsername());
	}

	/**
	 * Validate a token
	 * @param token - The current token
	 * @param userDetails - The UserDetails to be evaluated
	 * @return Boolean
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = this.getUsernameFromToken(token);
		return (userDetails.getUsername().equals(username) && !this.isTokenExpired(token));
	}

	/**
	 * Get the username associated to a token
	 * @param token - The token to be processed
	 * @return String
	 */
	public String getUsernameFromToken(String token) {
		return this.retrieveClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * Get the expiration date from a token
	 * @param token - The token to be processed
	 * @return Date
	 */
	public Date getExpirationDateFromToken(String token) {
		return this.retrieveClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * Used to check if the "claim" is from the right token signer.
	 * @param token - The token to be processed
	 * @param claimsResolver - The claim resolver
	 * @return <T> T
	 */
	public <T> T retrieveClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = this.retrieveAllClaimsFromToken(token);

		return claimsResolver.apply(claims);
	}

	/**
	 * Parse the "claim" from the right token signer.
	 * @param token - The token to be processed
	 * @return Claims
	 */
	private Claims retrieveAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(AppConstants.TOKEN_SIGNING_KEY).parseClaimsJws(token).getBody();
	}

	/**
	 * Check if the token is expired.
	 * @param token - The token to be processed
	 * @return Boolean
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	/**
	 * Generate a new token based on the username.
	 * @param username - The username to be used
	 * @return String
	 */
	private String doGenerateToken(String username) {
		Claims claims = Jwts.claims().setSubject(username);

		claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

		return Jwts.builder()
				.setClaims(claims)
				.setIssuer(AppConstants.TOKEN_ISSUER)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + AppConstants.TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, AppConstants.TOKEN_SIGNING_KEY)
				.compact();
	}
}