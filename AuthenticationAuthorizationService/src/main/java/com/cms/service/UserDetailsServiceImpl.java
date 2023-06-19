package com.cms.service;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import com.cms.model.Login;
import com.cms.repository.UserRepository;

// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {

// 	UserRepository userRepository;

// 	@Override
// 	@Transactional
// 	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// 		return null;
// 	}

// }

// package com.service;

// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Login userOp = userRepository.findByUsername(username);
		if (userOp == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		} else {
			return getUser(username, userOp);
		}
	}

	private UserDetails getUser(String username, Login user) {
		return UserDetailsImpl.getUser(user);
	}

}
