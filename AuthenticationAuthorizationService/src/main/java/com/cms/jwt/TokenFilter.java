package com.cms.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cms.service.UserDetailsServiceImpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class TokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtility jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  // @Override
  // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
  //     throws ServletException, IOException {
  //   String token = request.getHeader("Authorization");
  //   if (token != null && token.startsWith("Bearer ")) {
  //     token = token.substring(7);
  //     if (!jwtUtils.validateToken(token)) {
  //       String username = jwtUtils.getUsernameFromToken(token);
  //       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
  //           userDetailsService.loadUserByUsername(username), null,
  //           userDetailsService.loadUserByUsername(username).getAuthorities());
  //       SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  //     }
  //   }
  //   filterChain.doFilter(request, response);
  // }


  @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = jwtUtils.getUsernameFromToken(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            if (jwtUtils.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


}
