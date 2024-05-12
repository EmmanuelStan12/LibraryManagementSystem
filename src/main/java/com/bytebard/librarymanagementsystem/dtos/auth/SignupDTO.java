package com.bytebard.librarymanagementsystem.dtos.auth;

import com.bytebard.librarymanagementsystem.validators.ValidEmail;
import com.bytebard.librarymanagementsystem.validators.ValidPassword;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupDTO {

    @NotEmpty(message = "Firstname cannot be empty")
    private String firstname;

    @NotEmpty(message = "Lastname cannot be empty")
    private String lastname;

    @ValidEmail
    private String email;

    @ValidPassword
    private String password;

    @NotEmpty(message = "Username cannot be empty")
    private String username;
}
