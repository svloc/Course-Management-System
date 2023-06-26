package com.cms.controller;

import java.util.ArrayList;
import java.util.List;
// import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.jwt.JwtUtility;
import com.cms.repository.UserRepository;
import com.cms.request.LoginRequest;
import com.cms.response.JSONResponse;
import com.cms.service.UserDetailsServiceImpl;

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

	@PostMapping("/signin")
	public ResponseEntity<?> validateUser(@RequestBody LoginRequest loginRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginRequest.getUsername());

			String token = jwtTokenUtil.generateToken(authentication);
			List<String> roles = new ArrayList<>();
			for (GrantedAuthority authority : userDetails.getAuthorities()) {
				roles.add(String.valueOf(authority));
			}
			JSONResponse jsonResponse = new JSONResponse(token, userDetails.getUsername(), roles);

			return ResponseEntity.ok(jsonResponse);
		} catch (Exception authExc) {
			throw new RuntimeException(authExc.getMessage());
		}

	}
	@GetMapping("/validateToken/{authorization}")
	public boolean isValidToken(@PathVariable String authorization) {
		String token = authorization.substring(7);
		if (jwtTokenUtil.validateToken(token)) {
			return true;
		} else {
			return false;
		}
	}

}
