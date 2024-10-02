package com.zionique.invoiceapp.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//@Component
//public class JwtUtil {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration}")
//    private long expirationTime;
//
//    public String generateToken(String mobile) {
//        return Jwts.builder()
//                .setSubject(mobile)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
//
//    public String getMobileFromToken(String token) {
//        return extractClaim(token, Claims::getSubject);
////        return Jwts.parser()
////                .setSigningKey(secret)
////                .parseClaimsJws(token)
////                .getBody()
////                .getSubject();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
//            return true;
//        } catch (JwtException e) {
//            return false;
//        }
//    }
//}
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;
//    private String SECRET_KEY = "your_secret_key"; // Make sure to use a strong secret key

    // Extract the mobile number (username) from the JWT token
    public String getMobileFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from the JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract a claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method to extract all claims from the token using the updated JWT API
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the claims body
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate a token for a specific user (mobile number)
    public String generateToken(String mobile) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, mobile);
    }

    // Create the token with claims, subject, and expiration details
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Validate the token
    public Boolean validateToken(String token, String mobile) {
        final String username = getMobileFromToken(token);
        return (username.equals(mobile) && !isTokenExpired(token));
    }
}

