package com.bytebard.librarymanagementsystem.mappers;


import com.bytebard.librarymanagementsystem.dtos.UserDTO;
import com.bytebard.librarymanagementsystem.models.User;

public class UserMapper {

    public static UserDTO toUserDTO(final User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getBio(),
                user.getUsername()
        );
    }
}
