package com.bytebard.librarymanagementsystem.services.impl;

import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.mappers.UserMapper;
import com.bytebard.librarymanagementsystem.models.User;
import com.bytebard.librarymanagementsystem.repository.UserRepository;
import com.bytebard.librarymanagementsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User findByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailOrUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username does not exist.");
        }
        return user.get();
    }

    @Transactional
    public User register(User user) throws AlreadyExistsException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new AlreadyExistsException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
