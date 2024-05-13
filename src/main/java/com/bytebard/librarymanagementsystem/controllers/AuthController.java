package com.bytebard.librarymanagementsystem.controllers;

import com.bytebard.librarymanagementsystem.config.security.JwtAuthenticationTokenProvider;
import com.bytebard.librarymanagementsystem.dtos.ApiResponse;
import com.bytebard.librarymanagementsystem.dtos.auth.AuthDTO;
import com.bytebard.librarymanagementsystem.dtos.auth.LoginDTO;
import com.bytebard.librarymanagementsystem.dtos.auth.CreateUserDTO;
import com.bytebard.librarymanagementsystem.mappers.UserMapper;
import com.bytebard.librarymanagementsystem.models.User;
import com.bytebard.librarymanagementsystem.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
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
    public ResponseEntity<ApiResponse<AuthDTO>> login(@RequestBody LoginDTO loginDTO) {
        User user = authenticationProvider.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
        AuthDTO response = generateAuthResponse(user);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Login successful", response), HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponse<AuthDTO>> signup(@Valid  @RequestBody CreateUserDTO createUserDTO) throws Exception {
        User user = userMapper.convertToModel(createUserDTO);
        user = userService.register(user);
        authenticationProvider.verifyUser(user);
        AuthDTO response = generateAuthResponse(user);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Signup successful", response), HttpStatus.OK);
    }

    private AuthDTO generateAuthResponse(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new AuthDTO(userMapper.convertToDTO(user), authentication.getCredentials().toString());
    }
}
