package com.bytebard.librarymanagementsystem.services;

import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User findByUsername(String username);

    User register(User user) throws AlreadyExistsException;
}
