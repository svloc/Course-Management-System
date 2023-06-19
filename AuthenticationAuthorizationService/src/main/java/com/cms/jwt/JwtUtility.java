 package com.cms.jwt;

// import org.springframework.security.core.Authentication;




// public class JwtUtility {
	

	
// 	private String jwtSecret;

// 	private int jwtExpirationMs;

// 	public String generateToken(Authentication authentication) {

// 		return null;
// 	}

	
// }


// package com.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cms.service.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtility {
	
	@Value("${jwtSecret}")
	private String jwtSecret;

	@Value("${jwtExpirationMs}")
	private int jwtExpirationMs;
    
    Logger logger = LoggerFactory.getLogger(JwtUtility.class);
    
	public String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs); 
        
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
	            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
            
        return claims.getSubject();
    }
    
    public Boolean validateToken(String token) {
     try {
        getClaim(token);
        return true;
     } catch (Exception ex) {
        return false;
     }
     
  }
  public Claims getClaim(String token) {
     return (Claims) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
     }
    
}

