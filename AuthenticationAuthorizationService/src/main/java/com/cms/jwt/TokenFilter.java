package com.cms.jwt;

// import org.springframework.web.filter.OncePerRequestFilter;
// import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// public class TokenFilter extends OncePerRequestFilter {

// @Override
// protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
// 		throws ServletException, IOException {
	
// }
// }



// package com.jwt;

import java.io.IOException;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cms.service.UserDetailsServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class TokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtility jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;
  
  Logger log = LoggerFactory.getLogger(TokenFilter.class);
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
     logger.debug("============== filter ===================");
     String token = request.getHeader("Authorization");
     if (token != null && token.startsWith("Bearer ")) {
        token = token.substring(7);
            if (!jwtUtils.validateToken(token)) {
                logger.debug("================ valid token ======================");
                String username = jwtUtils.getUsernameFromToken(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetailsService.loadUserByUsername(username), null, userDetailsService.loadUserByUsername(username).getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }   
         else {
                log.debug("================ invalid token =================");
             }
        }
        filterChain.doFilter(request, response);
  }

  
}
