package com.bytebard.librarymanagementsystem.services.impl;

import com.bytebard.librarymanagementsystem.mappers.UserMapper;
import com.bytebard.librarymanagementsystem.models.User;
import com.bytebard.librarymanagementsystem.repository.UserRepository;
import com.bytebard.librarymanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByEmailOrUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username does not exist.");
        }
        return user.get();
    }

    public User register(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
