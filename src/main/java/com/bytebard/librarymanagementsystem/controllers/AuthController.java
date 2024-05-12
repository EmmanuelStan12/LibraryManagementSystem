package com.bytebard.librarymanagementsystem.controllers;

import com.bytebard.librarymanagementsystem.config.security.JwtAuthenticationTokenProvider;
import com.bytebard.librarymanagementsystem.dtos.ApiResponse;
import com.bytebard.librarymanagementsystem.dtos.auth.LoginDTO;
import com.bytebard.librarymanagementsystem.dtos.auth.SignupDTO;
import com.bytebard.librarymanagementsystem.mappers.UserMapper;
import com.bytebard.librarymanagementsystem.models.User;
import com.bytebard.librarymanagementsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final UserService userService;

    private final JwtAuthenticationTokenProvider authenticationProvider;

    private final UserMapper userMapper;

    public AuthController(final UserService userService, JwtAuthenticationTokenProvider authenticationProvider, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
        this.userMapper = userMapper;
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginDTO loginDTO) {
        User user = authenticationProvider.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
        Map<String, Object> response = generateAuthResponse(user);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Login successful", response), HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<Object>> signup(@RequestBody SignupDTO signupDTO) throws Exception {
        User user = userMapper.convertToModel(signupDTO);
        user = userService.register(user);
        authenticationProvider.verifyUser(user);
        Map<String, Object> response = generateAuthResponse(user);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Signup successful", response), HttpStatus.OK);
    }

    private Map<String, Object> generateAuthResponse(User user) {
        Map<String, Object> response = new HashMap<>();
        response.put("user", userMapper.convertToDTO(user));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        response.put("token", authentication.getCredentials().toString());
        return response;
    }
}
