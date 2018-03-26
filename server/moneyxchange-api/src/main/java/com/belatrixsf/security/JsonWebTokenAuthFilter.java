package com.belatrixsf.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.belatrixsf.constants.AppConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * Used to handle all HTTP requests and look up for a token.
 */
public class JsonWebTokenAuthFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JsonWebTokenUtil jsonWebTokenUtil;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader(AppConstants.TOKEN_HTTP_HEADER_NAME);

		String username = null;
		String token = null;

		if (header != null && header.startsWith(AppConstants.TOKEN_HTTP_HEADER_PREFIX)) {
			token = header.replace(AppConstants.TOKEN_HTTP_HEADER_PREFIX, "");

			try {
				username = jsonWebTokenUtil.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				logger.error("doFilterInternal() - An error occurred while retrieving the username from the token.", e);
			} catch (ExpiredJwtException e) {
				logger.error("doFilterInternal() - Invalid token found, it might be expired.", e);
			} catch (SignatureException e) {
				logger.error("doFilterInternal() - Invalid username or password.", e);
			}
		} else {
			logger.warn("doFilterInternal() - The token cannot be found.");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jsonWebTokenUtil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, 
						null, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				logger.info("doFilterInternal() - The user=\"" + username +"\" is now authenticated.");

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}
}