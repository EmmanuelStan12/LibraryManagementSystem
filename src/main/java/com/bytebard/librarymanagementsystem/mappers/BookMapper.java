package com.bytebard.librarymanagementsystem.mappers;

import com.bytebard.librarymanagementsystem.dtos.book.BookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.CreateBookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.UpdateBookDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.CreatePatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.UpdatePatronDTO;
import com.bytebard.librarymanagementsystem.models.Book;
import com.bytebard.librarymanagementsystem.models.Patron;
import com.bytebard.librarymanagementsystem.models.enums.Genre;
import com.bytebard.librarymanagementsystem.utils.Extensions;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<Book, BookDTO> {

    @Override
    public BookDTO convertToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishedDate(),
                book.getDescription(),
                book.getPrice(),
                book.getGenre().getName(),
                book.getIsbn()
        );
    }

    @Override
    public Book convertToModel(BookDTO bookDTO) {
        return new Book(
                bookDTO.getId(),
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getPublishedDate(),
                bookDTO.getDescription(),
                bookDTO.getPrice(),
                Genre.fromString(bookDTO.getGenre()),
                bookDTO.getIsbn()
        );
    }

    public Book convertToModel(CreateBookDTO bookDTO) {
        return new Book(
                null,
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getPublishedDate(),
                bookDTO.getDescription(),
                bookDTO.getPrice(),
                Genre.fromString(bookDTO.getGenre()),
                bookDTO.getIsbn()
        );
    }

    public Book copyDTOToModel(UpdateBookDTO src, Book dest) {
        Extensions.executeIfNotBlank(src.getDescription(), dest::setDescription);
        Extensions.executeIfNotBlank(src.getAuthor(), dest::setAuthor);
        Extensions.executeIfNotBlank(src.getTitle(), dest::setTitle);
        Extensions.executeIfNotBlank(src.getIsbn(), dest::setIsbn);
        Extensions.executeIfNotNull(src.getPrice(), dest::setPrice);
        Extensions.executeIfNotNull(src.getPublishedDate(), dest::setPublishedDate);
        Extensions.executeIfNotBlank(src.getGenre(), (str) -> dest.setGenre(Genre.fromString(str)));
        return dest;
    }
}
