package com.cms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.cms.repository.UserRepository;
import com.cms.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Test
	public void test118LoadByUserName() {
		String username = "admin";
		String password = "password";

		// Request
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

		// Assert
		Assertions.assertNotNull(userDetails);
		Assertions.assertEquals(username, userDetails.getUsername());
		Assertions.assertEquals(password, userDetails.getPassword());
	}

	// Test whether loadByUserName throws Exception for invalid user
	@Test
	public void test118LoadByUserNameForInvalidUser() {
		String username = "invalid_user";

		// Assert
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsServiceImpl.loadUserByUsername(username);
		});
	}

}