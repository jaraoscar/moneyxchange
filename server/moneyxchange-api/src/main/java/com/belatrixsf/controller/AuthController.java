package com.belatrixsf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.belatrixsf.model.User;
import com.belatrixsf.model.UserToken;
import com.belatrixsf.security.JsonWebTokenUtil;
import com.belatrixsf.service.UserService;

/**
 * The authentication controller.
 */
@RestController
@RequestMapping("/token")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JsonWebTokenUtil jsonWebTokenUtil;

	@Autowired
	private UserService userService;

	/**
	 * Authenticates a user by generating a token.
	 * @param loginUser - The User entity containing the username and password to be processed
	 * @return ResponseEntity<?>
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody User loginUser) throws AuthenticationException {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
		
		final Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final User user = userService.findByUsername(loginUser.getUsername());
		final String token = jsonWebTokenUtil.generateToken(user);

		return ResponseEntity.ok(new UserToken(token));
	}
}