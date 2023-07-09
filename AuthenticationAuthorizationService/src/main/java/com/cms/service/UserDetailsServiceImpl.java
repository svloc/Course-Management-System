package com.cms.service;

import com.cms.model.Login;
import com.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Login userOp = userRepository.findByUsername(username);
		if (userOp == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		return getUser(userOp);
		
	}

	public UserDetails getUser(Login user) {
		return UserDetailsImpl.getUser(user);
	}

}
