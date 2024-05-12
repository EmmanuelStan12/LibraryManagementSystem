package com.bytebard.librarymanagementsystem.services;

import com.bytebard.sharespace.user.UserMapper;
import com.bytebard.sharespace.user.data.UserEntity;
import com.bytebard.sharespace.user.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByEmailOrUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username does not exist.");
        }
        return UserMapper.toUser(user.get());
    }

    public User register(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toUser(userRepository.save(UserMapper.toUserEntity(user)));
    }
}
