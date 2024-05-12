package com.bytebard.librarymanagementsystem.services;

import com.bytebard.librarymanagementsystem.dtos.book.BookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.CreateBookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.UpdateBookDTO;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.models.Book;
import com.bytebard.librarymanagementsystem.models.enums.Genre;
import com.bytebard.librarymanagementsystem.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    public void test_GetBookById() throws NotFoundException {
        Long id = createTestBook();
        BookDTO book = bookService.getBookById(id);
        assertEquals(book.getId(), id);
    }

    @Test
    public void test_GetBookById_ThrowsException() {
        Long id = createTestBook();
        assertThrows(NotFoundException.class, () -> bookService.getBookById(id + 1));
    }

    @Test
    public void test_GetBooks() {
        createTestBook();
        List<BookDTO> books = bookService.getAllBooks();
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
    }

    @Test
    public void test_SaveBook() {
        CreateBookDTO createBookDTO = new CreateBookDTO(
                "test.title.1",
                "test.author.1",
                LocalDate.now(),
                "test.description.1",
                12000,
                "Art",
                "test.isbn.1"
        );
        BookDTO book = bookService.createBook(createBookDTO);
        assertNotNull(book.getId());
        assertEquals("test.title.1", book.getTitle());
    }

    @Test
    public void test_UpdateBook() {
        Long id = createTestBook();
        UpdateBookDTO bookDTO = new UpdateBookDTO(
                "test.title.updated",
                "test.author.updated",
                LocalDate.now(),
                "test.description.updated",
                12000,
                "Art",
                "test.isbn.1"
        );
        assertDoesNotThrow(() -> {
            BookDTO book = bookService.updateBook(bookDTO, id);
            assertEquals(book.getId(), id);
            assertEquals("test.title.updated", book.getTitle());
            assertEquals("test.author.updated", book.getAuthor());
            assertEquals("test.description.updated", book.getDescription());
        });
    }

    @Test
    public void test_UpdateBook_NonExistentBook() {
        Long id = createTestBook();
        UpdateBookDTO bookDTO = new UpdateBookDTO(
                "test.title.updated",
                "test.author.updated",
                LocalDate.now(),
                "test.description.updated",
                12000,
                "Art",
                "test.isbn.1"
        );
        assertThrows(NotFoundException.class, () -> bookService.updateBook(bookDTO, id + 1));
    }

    @Test
    public void test_DeleteBook_NonExistentBook() {
        Long id = createTestBook();
        assertThrows(NotFoundException.class, () -> bookService.deleteBook(id + 1));
    }

    @Test
    public void test_DeleteBook() {
        Long id = createTestBook();
        assertDoesNotThrow(() -> bookService.deleteBook(id));
        assertThrows(NotFoundException.class, () -> bookService.getBookById(id));
    }

    private Long createTestBook() {
        Book book = new Book(
                null,
                "test.title",
                "test.author",
                LocalDate.now(),
                "test.description",
                12000,
                Genre.ART,
                "test.isbn"
        );

        book = bookRepository.save(book);
        return book.getId();
    }
}
