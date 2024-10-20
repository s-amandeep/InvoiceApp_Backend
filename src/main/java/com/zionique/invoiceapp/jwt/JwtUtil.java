package com.zionique.invoiceapp.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    private Key jwtSecretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret); // Decode the base64-encoded key
        jwtSecretKey = Keys.hmacShaKeyFor(keyBytes); // Create a secure key for HS256
    }

    // Extract the mobile number (username) from the JWT token
    public String getMobileFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles").toString();
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
                .setSigningKey(jwtSecretKey)
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the claims body
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate a token for a specific user (mobile number)
    public String generateToken(UserDetails userDetails, List<String> roles, String name) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("Role", )
        // Add roles to the JWT token
//        claims.put("roles", userDetails.getAuthorities().iterator().next().getAuthority());
        claims.put("name", name);
        claims.put("roles", roles);
        return createToken(claims, userDetails.getUsername());
    }

    // Create the token with claims, subject, and expiration details
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 10 hours expiration
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate the token
    public Boolean validateToken(String token, String mobile) {
        final String username = getMobileFromToken(token);
//        try {
//            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
        return (username.equals(mobile) && !isTokenExpired(token));
    }
}

