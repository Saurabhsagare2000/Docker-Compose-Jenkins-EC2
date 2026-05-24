package com.blog.Security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String secret =
            "VGhpc0lzQVN1cGVyU2VjdXJlS2V5Rm9ySldUVG9rZW5BdXRoZW50aWNhdGlvbjEyMzQ1Njc4OTA=";

    private Key getSignKey() {
        byte[] keyBytes =
                Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(
            String token) {

        return getClaimFromToken(
                token,
                Claims::getSubject
        );
    }

    public Date getExpirationDateFromToken(
            String token) {

        return getClaimFromToken(
                token,
                Claims::getExpiration
        );
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimsResolver) {

        final Claims claims =
                getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(
            String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(
            String token) {

        final Date expiration =
                getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

    public String generateToken(
            UserDetails userDetails) {

        Map<String, Object> claims =
                new HashMap<>();

        return createToken(
                claims,
                userDetails.getUsername()
        );
    }

    private String createToken(
            Map<String, Object> claims,
            String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + JWT_TOKEN_VALIDITY * 1000
                        )
                )
                .signWith(
                        getSignKey(),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public Boolean validateToken(
            String token,
            UserDetails userDetails) {

        final String username =
                getUsernameFromToken(token);

        return username.equals(
                userDetails.getUsername()
        ) && !isTokenExpired(token);
    }
}