package com.cms.controller;

import java.util.ArrayList;
// import java.util.Collection;
import java.util.List;
// import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cms.jwt.JwtUtility;
import com.cms.repository.UserRepository;
import com.cms.request.LoginRequest;
import com.cms.response.JSONResponse;
import com.cms.service.UserDetailsServiceImpl;
import com.cms.model.Login;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/app")
public class AuthController {

	@Autowired
	private JwtUtility jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signin")
	public ResponseEntity<?> validateUser(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginRequest.getUsername());

			String token = jwtTokenUtil.generateToken(authentication);

			List<String> roles = new ArrayList<>();
			for (GrantedAuthority authority : userDetails.getAuthorities()) {
				roles.add(authority.getAuthority());
			}
			JSONResponse jsonResponse = new JSONResponse(token, userDetails.getUsername(), roles);

			return ResponseEntity.ok(jsonResponse);
		} catch (Exception authExc) {
			throw new RuntimeException(authExc.getMessage());
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<String> addUser(@RequestBody Login signupRequest) {
		Login loginObj = repository.findByUsername(signupRequest.getUsername());
		if (loginObj != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
		}
		signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		signupRequest.setRole("ROLE_USER");
		Login response = repository.save(signupRequest);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user");
		}
	}

	@GetMapping("/validateToken/{authorization}")
	public boolean isValidToken(@PathVariable String authorization) {
		String token = authorization.substring(7);
		return jwtTokenUtil.validateToken(token);
	}

	@PostMapping("/validateTokenForAdmin/{authorization}")
	public boolean isValidToken(@PathVariable String authorization, @RequestParam("role") String role) {
		String token = authorization.substring(7);
		if (jwtTokenUtil.validateToken(token)) {
			List<String> roles = jwtTokenUtil.extractRoles(token);
			String rolesString = String.join(",", roles);
			return rolesString.equals(role);
		} else {
			return false;
		}
	}

}
