package com.cms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.AuthController;
import com.cms.jwt.JwtUtility;
import com.cms.request.LoginRequest;
import com.cms.service.UserDetailsImpl;
import com.cms.service.UserDetailsServiceImpl;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;

@RunWith(SpringRunner.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private JwtUtility jwtTokenUtil;

    @MockBean
    private Authentication authentication;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @InjectMocks
    private AuthController authController;

    @MockBean
	private AuthenticationManager authenticationManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    // 1. Test whether the endpoint /app/signin is successful
    // @Test
    // public void test202RestApiCallForTokenGeneration() throws Exception {
    // String token = "sample_token";
    // String username = "admin";
    // String password="password123";

    // LoginRequest loginRequest=new LoginRequest();
    // loginRequest.setUsername(username);
    // loginRequest.setPassword(password);

    // //
    // when(jwtTokenUtil.generateToken(any(Authentication.class))).thenReturn(token);
    // // when(authentication.getName()).thenReturn(username);
    // //
    // when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(any(UserDetails.class));
    // when(authController.validateUser(loginRequest)).thenReturn(username);

    // mockMvc.perform(post("/app/signin"))
    // .andExpect(status().isOk())
    // .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    // }
    @Test
    public void test202RestApiCallForTokenGeneration() throws Exception {
        String token = "sample_token";
        String username = "admin";
        String password = "password";

        // Mock the login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        // Mock the authentication and user details
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        UserDetails userDetails = new UserDetailsImpl(username, password, Collections.emptyList());

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(userDetailsServiceImpl.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(any(Authentication.class))).thenReturn(token);

        // Perform the request and validate the response
        mockMvc.perform(post("/app/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
                // .andExpect(jsonPath("$.token").value(token));
                // .andExpect(jsonPath("$.username").value(username))
                // .andExpect(jsonPath("$.roles").isArray());
    }

    // Test whether the endpoint /app/validateToken/{authorization} is successful
    @Test
    public void test203RestApiCallForValidatingToken() throws Exception {
        String authorization = "Bearer sample_token";

        when(jwtTokenUtil.validateToken("sample_token")).thenReturn(true);

        this.mockMvc.perform(get("/app/validateToken/{authorization}", authorization))
                .andExpect(status().isOk());
    }

}
