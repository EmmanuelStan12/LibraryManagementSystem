package com.bytebard.librarymanagementsystem.dtos.patron;

import com.bytebard.librarymanagementsystem.dtos.loan.LoanDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatronDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate registrationDate;

}
