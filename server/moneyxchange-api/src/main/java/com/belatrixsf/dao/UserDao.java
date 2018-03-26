package com.belatrixsf.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.belatrixsf.model.User;

/**
 * The UserDao class.
 */
@Repository
public interface UserDao extends CrudRepository<User, Long> {
	User findByUsername(String username);
}