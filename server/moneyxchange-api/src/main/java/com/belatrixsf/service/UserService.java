package com.belatrixsf.service;

import java.util.List;
import java.util.Optional;

import com.belatrixsf.model.User;

/**
 * The UserService interface with the contract definition.
 */
public interface UserService {

	/**
	 * Create a new user.
	 * @param user - The User entity to be processed
	 * @return User
	 */
    User save(User user);

    /**
     * Delete a user by Id.
     * @param id - The User Id.
     */
    void deleteById(long id);
    
	/**
	 * Get all users.
	 * @return List<User>
	 */
    List<User> findAll();
    
	/**
	 * Get a user by Id.
	 * @param id - The User Id
	 * @return Optional<User>
	 */
    Optional<User> findById(Long id);
    
    /**
     * Find a User by username.
     * @param username - The username to be found
     * @return User
     */
    User findByUsername(String username);
}