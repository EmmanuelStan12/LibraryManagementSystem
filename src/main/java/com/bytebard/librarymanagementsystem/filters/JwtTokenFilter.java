package com.bytebard.librarymanagementsystem.filters;

import com.bytebard.librarymanagementsystem.config.security.JwtAuthenticationToken;
import com.bytebard.librarymanagementsystem.config.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final HandlerExceptionResolver exceptionResolver;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtUtil jwtUtil, HandlerExceptionResolver exceptionResolver, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.exceptionResolver = exceptionResolver;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        try {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                String token = authorization.substring("Bearer ".length());
                jwtUtil.validateToken(token);
                String username = jwtUtil.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails, token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            exceptionResolver.resolveException(request, response, null, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
