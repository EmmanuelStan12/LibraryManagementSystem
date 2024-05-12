package com.bytebard.librarymanagementsystem.services.impl;

import com.bytebard.librarymanagementsystem.dtos.book.BookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.CreateBookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.UpdateBookDTO;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.mappers.BookMapper;
import com.bytebard.librarymanagementsystem.models.Book;
import com.bytebard.librarymanagementsystem.repository.BookRepository;
import com.bytebard.librarymanagementsystem.services.BookService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(final BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::convertToDTO).toList();
    }

    @Override
    public BookDTO getBookById(long id) throws NotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
        return bookMapper.convertToDTO(book.get());
    }

    @Override
    public BookDTO createBook(CreateBookDTO bookDTO) {
        Book book = bookMapper.convertToModel(bookDTO);
        book = bookRepository.save(book);
        return bookMapper.convertToDTO(book);
    }

    @Override
    public BookDTO updateBook(UpdateBookDTO bookDTO, Long bookId) throws NotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            throw new NotFoundException("Book with id " + bookId + " not found");
        }
        Book book = bookOptional.get();
        book = bookMapper.copyDTOToModel(bookDTO, book);
        book = bookRepository.save(book);
        return bookMapper.convertToDTO(book);
    }

    @Override
    public void deleteBook(long id) throws NotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isEmpty()) {
            throw new NotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}
