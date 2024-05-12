package com.bytebard.librarymanagementsystem.services;

import com.bytebard.librarymanagementsystem.dtos.book.BookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.CreateBookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.UpdateBookDTO;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;

import java.util.List;

public interface BookService {

    List<BookDTO> getAllBooks();

    BookDTO getBookById(long id) throws NotFoundException;

    BookDTO createBook(CreateBookDTO book);

    BookDTO updateBook(UpdateBookDTO book, Long id) throws NotFoundException;

    void deleteBook(long id) throws NotFoundException;
}
