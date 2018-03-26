package com.belatrixsf.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.belatrixsf.model.User;
import com.belatrixsf.service.UserService;

/**
 * The UserController used for handling the user actions.
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * Get all users.
	 * @return List<User>
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userService.findAll();
	}

	/**
	 * Get a user by Id.
	 * @param id - The User Id
	 * @return Optional<User>
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public Optional<User> findById(@PathVariable(value = "id") Long id) {
		return userService.findById(id);
	}
	
	/**
	 * Delete a user by Id.
	 * @param id - The User Id
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable(value = "id") long id) {
		userService.deleteById(id);
	}

	/**
	 * Create a new user.
	 * @param user - The User entity to be processed
	 * @return User
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public User saveUser(@RequestBody User user) {
		return userService.save(user);
	}
}
