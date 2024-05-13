package com.bytebard.librarymanagementsystem.controllers;

import com.bytebard.librarymanagementsystem.dtos.ApiResponse;
import com.bytebard.librarymanagementsystem.dtos.book.CreateBookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.BookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.UpdateBookDTO;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.services.BookService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @CachePut("books")
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAll() {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), null, bookService.getAllBooks()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable final Long id) throws NotFoundException {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), null, bookService.getBookById(id)),
                HttpStatus.OK
        );
    }

    @PostMapping
    @CacheEvict(value = "books", allEntries = true)
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@Valid  @RequestBody final CreateBookDTO createBookDTO) {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), null, bookService.createBook(createBookDTO)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "books", allEntries = true)
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(@PathVariable final Long id, @RequestBody final UpdateBookDTO updateBookDTO) throws NotFoundException {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), null, bookService.updateBook(updateBookDTO, id)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "books", allEntries = true)
    public ResponseEntity<ApiResponse<BookDTO>> deleteBook(@PathVariable final Long id) throws NotFoundException {
        bookService.deleteBook(id);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "Book deleted successfully", null),
                HttpStatus.OK
        );
    }
}
