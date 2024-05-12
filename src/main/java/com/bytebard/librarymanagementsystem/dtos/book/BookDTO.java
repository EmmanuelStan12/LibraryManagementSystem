package com.bytebard.librarymanagementsystem.dtos.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDTO {

    private Long id;

    private String title;

    private String author;

    private LocalDate publishedDate;

    private String description;

    private double price;

    private String genre;

    private String isbn;

}
