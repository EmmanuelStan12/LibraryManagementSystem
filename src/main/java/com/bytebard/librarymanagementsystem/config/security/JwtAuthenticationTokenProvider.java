package com.bytebard.librarymanagementsystem.config.security;

import com.bytebard.librarymanagementsystem.models.User;
import com.bytebard.librarymanagementsystem.services.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JwtAuthenticationTokenProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationTokenProvider(final UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User authenticate(String username, String password) throws AuthenticationException {
        User user = userService.findByUsername(username);
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new BadCredentialsException("Email (Username)/Password is/are incorrect");
        }

        verifyUser(user);
        return user;
    }

    public void verifyUser(User user) {
        UserDetails userDetails = new JpaUserDetails(user);
        String token = jwtUtil.generateToken(user.getUsername());
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
