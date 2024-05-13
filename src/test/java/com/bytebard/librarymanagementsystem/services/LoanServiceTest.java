package com.bytebard.librarymanagementsystem.services;

import com.bytebard.librarymanagementsystem.dtos.book.BookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.CreateBookDTO;
import com.bytebard.librarymanagementsystem.dtos.book.UpdateBookDTO;
import com.bytebard.librarymanagementsystem.dtos.loan.LoanDTO;
import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.models.Book;
import com.bytebard.librarymanagementsystem.models.Patron;
import com.bytebard.librarymanagementsystem.models.enums.Genre;
import com.bytebard.librarymanagementsystem.repository.BookRepository;
import com.bytebard.librarymanagementsystem.repository.PatronRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class LoanServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private PatronRepository patronRepository;

    @Test
    public void test_loanBook_NonExistentPatron() {
        Long bookId = createTestBook();
        assertThrows(NotFoundException.class, () -> loanService.loanBook(bookId, 1L));
    }

    @Test
    public void test_loanBook_NonExistentBook() {
        Long patronId = createTestPatron();
        assertThrows(NotFoundException.class, () -> loanService.loanBook(1L, patronId));
    }

    @Test
    public void test_loanBook() {
        Long bookId = createTestBook();
        Long patronId = createTestPatron();
        assertDoesNotThrow(() -> {
            LoanDTO loan = loanService.loanBook(bookId, patronId);
            assertNotNull(loan);
            assertEquals(bookId, loan.getBook().getId());
            assertEquals(patronId, loan.getPatron().getId());
            assertNull(loan.getReturnedDate());
        });
    }

    @Test
    public void test_loanBook_thatIsAlreadyLoanedByAnotherPatron() {
        Long bookId = createTestBook();
        Long patronId1 = createTestPatron();
        Long patronId2 = createTestPatron();
        assertDoesNotThrow(() -> loanService.loanBook(bookId, patronId1));
        assertThrows(AlreadyExistsException.class, () -> loanService.loanBook(bookId, patronId2));
    }

    @Test
    public void test_returnBook_NonExistentPatron() {
        Long bookId = createTestBook();
        assertThrows(NotFoundException.class, () -> loanService.returnBook(bookId, 1L));
    }

    @Test
    public void test_returnBook_NonExistentBook() {
        Long patronId = createTestPatron();
        assertThrows(NotFoundException.class, () -> loanService.returnBook(1L, patronId));
    }

    @Test
    public void test_returnBook() {
        Long bookId = createTestBook();
        Long patronId = createTestPatron();
        assertDoesNotThrow(() -> loanService.loanBook(bookId, patronId));
        assertDoesNotThrow(() -> {
            LoanDTO loan = loanService.returnBook(bookId, patronId);
            assertNotNull(loan.getReturnedDate());
        });
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

    private Long createTestPatron() {
        Patron patron = new Patron(
                null,
                "test.firstname",
                "test.lastname",
                "test.email",
                "test.phone",
                LocalDate.now(),
                List.of()
        );

        patron = patronRepository.save(patron);
        return patron.getId();
    }
}
