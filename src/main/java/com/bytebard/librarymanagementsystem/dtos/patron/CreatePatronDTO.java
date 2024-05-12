package com.bytebard.librarymanagementsystem.dtos.patron;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePatronDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

}
