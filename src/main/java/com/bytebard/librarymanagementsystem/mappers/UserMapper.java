package com.bytebard.librarymanagementsystem.mappers;


import com.bytebard.librarymanagementsystem.dtos.auth.SignupDTO;
import com.bytebard.librarymanagementsystem.dtos.auth.UserDTO;
import com.bytebard.librarymanagementsystem.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getUsername()
        );
    }

    @Override
    public User convertToModel(UserDTO userDTO) {
        return null;
    }

    public User convertToModel(final SignupDTO dto) {
        return new User(
                null,
                dto.getFirstname(),
                dto.getLastname(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getUsername()
        );
    }
}
