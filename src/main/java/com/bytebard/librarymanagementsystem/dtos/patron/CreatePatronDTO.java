package com.bytebard.librarymanagementsystem.dtos.patron;

import com.bytebard.librarymanagementsystem.validators.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePatronDTO {

    @NotEmpty(message = "Firstname cannot be empty")
    private String firstName;

    @NotEmpty(message = "Lastname cannot be empty")
    private String lastName;

    @ValidEmail
    private String email;

    private String phone;

}
