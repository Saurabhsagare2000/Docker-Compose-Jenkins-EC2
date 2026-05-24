package com.blog.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.configration.UserDetailServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestTokenHeader =
                request.getHeader("Authorization");

        String username = null;
        String token = null;

        // JWT Token is in form "Bearer token"
        if (requestTokenHeader != null
                && requestTokenHeader.startsWith("Bearer ")) {

            token =
                    requestTokenHeader.substring(7);

            try {
                username =
                        this.jwtTokenHelper
                                .getUsernameFromToken(token);

            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Unable to get JWT Token"
                );

            } catch (ExpiredJwtException e) {
                System.out.println(
                        "JWT Token is expired"
                );

            } catch (MalformedJwtException e) {
                System.out.println(
                        "Invalid JWT Token"
                );
            }

        } else {
            System.out.println(
                    "JWT Token does not begin with Bearer"
            );
        }

        // Validate username and authenticate
        if (username != null
                && SecurityContextHolder
                .getContext()
                .getAuthentication() == null) {

            UserDetails userDetails =
                    this.userDetailServiceImpl
                            .loadUserByUsername(username);

            if (this.jwtTokenHelper.validateToken(
                    token,
                    userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                authenticationToken
                        );
            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}