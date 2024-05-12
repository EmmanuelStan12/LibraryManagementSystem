package com.bytebard.librarymanagementsystem.dtos.loan;

import com.bytebard.librarymanagementsystem.dtos.patron.PatronDTO;
import com.bytebard.librarymanagementsystem.dtos.book.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanDTO {

    private Long id;

    private PatronDTO patron;

    private BookDTO book;

    private LocalDate borrowedDate;

    private LocalDate returnedDate;
}
