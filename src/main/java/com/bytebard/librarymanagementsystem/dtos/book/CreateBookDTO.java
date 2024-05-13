package com.bytebard.librarymanagementsystem.dtos.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateBookDTO {

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Author cannot be empty")
    private String author;

    @NotNull(message = "Published date cannot be empty")
    private LocalDate publishedDate;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Price cannot be empty")
    private double price;

    @NotEmpty(message = "Genre cannot be empty")
    private String genre;

    @NotEmpty(message = "Isbn cannot be empty")
    private String isbn;

}
