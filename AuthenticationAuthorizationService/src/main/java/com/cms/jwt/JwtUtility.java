package com.cms.jwt;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cms.service.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.SignatureException;

@Component
@Slf4j
public class JwtUtility {

   @Value("${jwtSecret}")
   private String jwtSecret;

   @Value("${jwtExpirationMs}")
   private int jwtExpirationMs;

   public String generateToken(Authentication authentication) {
      UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
      Date now = new Date();
      Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
      return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date()).setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
   }

   public String getUsernameFromToken(String token) {
      Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
      return claims.getSubject();
   }

   public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
   }

   private Claims extractAllClaims(String token) {
      return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
   }

   public Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
   }

   private Boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
   }

   public Boolean validateToken(String token, UserDetails userDetails) {
      final String username = getUsernameFromToken(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }

   public Boolean validateToken(String token) {
      try {
         Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
         return true;
      } catch (SignatureException e) {
         log.error("Invalid JWT signature: {}", e.getMessage());
      } catch (MalformedJwtException e) {
         log.error("Invalid JWT token: {}", e.getMessage());
      } catch (ExpiredJwtException e) {
         log.error("JWT token is expired: {}", e.getMessage());
      } catch (UnsupportedJwtException e) {
         log.error("JWT token is unsupported: {}", e.getMessage());
      } catch (IllegalArgumentException e) {
         log.error("JWT claims string is empty: {}", e.getMessage());
      }
      return false;
   }

   public Claims getClaim(String token) {
      return (Claims) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
   }

}
