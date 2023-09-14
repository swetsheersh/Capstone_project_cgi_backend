package com.jwt.springjwt.JwtHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//methods for generating token
//validate
//isExp
//util class for jwt
//JwtUtil service class 
@Service
public class JwtUtil {
	//secret key
	private String SECRET_KEY = "JAVAqwertyuioasdfghjklzxcvbnmqwertyuioasdfghjklzxcvbnmmnbvcxz";

	//extract user name from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    //extract Expiry date of token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //claim resolver
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //extracting claim from token
	@SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
	
	//is token expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    //to generate token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    
    //method for generating token
    @SuppressWarnings("deprecation")
	private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    //method for validating token as well as used to extract username from token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
