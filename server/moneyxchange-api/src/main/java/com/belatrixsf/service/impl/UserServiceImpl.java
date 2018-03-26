package com.belatrixsf.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.belatrixsf.dao.UserDao;
import com.belatrixsf.model.User;
import com.belatrixsf.service.UserService;

/**
 * The implementation of the UserService interface.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	/*
	 * (non-Javadoc)
	 * @see com.belatrixsf.service.UserService#save(com.belatrixsf.model.User)
	 */
	@Override
	public User save(User user) {
		User newUser = new User();
		
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setName(user.getName());
		newUser.setLastname(user.getLastname());
		
		logger.info("save() - new user data: " + newUser.toString());
		
		return userDao.save(newUser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.belatrixsf.service.UserService#deleteById(long)
	 */
	@Override
	public void deleteById(long id) {
		userDao.deleteById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.belatrixsf.service.UserService#findAll()
	 */
	@Override
	public List<User> findAll() {
		List<User> list = new ArrayList<>();

		userDao.findAll().iterator().forEachRemaining(list::add);

		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see com.belatrixsf.service.UserService#findById(java.lang.Long)
	 */
	@Override
	public Optional<User> findById(Long id) {
		return userDao.findById(id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.belatrixsf.service.UserService#findByUsername(java.lang.String)
	 */
	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), this.getAuthority());
	}

	/**
	 * Get who's the authority.
	 * In this case: ROLE_ADMIN
	 * @return List<SimpleGrantedAuthority>
	 */
	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}
}