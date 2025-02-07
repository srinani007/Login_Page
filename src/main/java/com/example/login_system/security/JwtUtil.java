package com.example.login_system.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mySuperSecretKey"; // ✅ Change this in production

    // Generate Token with username and 10-hour validity
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10-hour validity
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign the token using HS256 and SECRET_KEY
                .compact(); // Generate the JWT token
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract any claim (like username or expiration) from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser() // ✅ Use parserBuilder() instead of parser()
                .setSigningKey(SECRET_KEY) // Set the signing key here
                .build() // Build the parser before using it
                .parseClaimsJws(token) // Parse the JWT token
                .getBody(); // Extract the claims body
    }

    // Validate if the token is correct (username matches and token is not expired)
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Check if the token has expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Check if expiration date is before current date
    }
}
