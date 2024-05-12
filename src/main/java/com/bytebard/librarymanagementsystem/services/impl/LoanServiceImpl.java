package com.bytebard.librarymanagementsystem.services.impl;

import com.bytebard.librarymanagementsystem.dtos.loan.LoanDTO;
import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.mappers.LoanMapper;
import com.bytebard.librarymanagementsystem.models.Book;
import com.bytebard.librarymanagementsystem.models.Loan;
import com.bytebard.librarymanagementsystem.models.Patron;
import com.bytebard.librarymanagementsystem.repository.BookRepository;
import com.bytebard.librarymanagementsystem.repository.LoanRepository;
import com.bytebard.librarymanagementsystem.repository.PatronRepository;
import com.bytebard.librarymanagementsystem.services.LoanService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    public LoanServiceImpl(LoanRepository loanRepository, LoanMapper loanMapper, BookRepository bookRepository, PatronRepository patronRepository) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    @Override
    public LoanDTO loanBook(Long bookId, Long patronId) throws NotFoundException, AlreadyExistsException {
        PrecheckResult result = loanPrecheck(bookId, patronId);
        Optional<Loan> existingLoan = loanRepository.findLoanByBookId(bookId);
        if (existingLoan.isPresent()) {
            throw new AlreadyExistsException("Loan for book with id " + bookId + " already exists. Please check back later.");
        }
        Loan loan = new Loan(
                null,
                result.patron().get(),
                result.book().get(),
                LocalDate.now(),
                null
        );
        loan = loanRepository.save(loan);

        return loanMapper.convertToDTO(loan);
    }

    @Override
    public LoanDTO returnBook(Long bookId, Long patronId) throws NotFoundException {
        loanPrecheck(bookId, patronId);
        Optional<Loan> existingLoan = loanRepository.findLoanByBookIdAndPatronId(bookId, patronId);
        if (existingLoan.isEmpty()) {
            throw new NotFoundException("Loan for book with id " + bookId + " does not exist.");
        }
        Loan loan = existingLoan.get();
        loan.setReturnedDate(LocalDate.now());
        loan = loanRepository.save(loan);

        return loanMapper.convertToDTO(loan);
    }

    private PrecheckResult loanPrecheck(Long bookId, Long patronId) throws NotFoundException {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new NotFoundException("Book with id " + bookId + " not found");
        }
        Optional<Patron> patron = patronRepository.findById(patronId);
        if (patron.isEmpty()) {
            throw new NotFoundException("Patron with id " + patronId + " not found");
        }
        PrecheckResult result = new PrecheckResult(book, patron);
        return result;
    }

    private record PrecheckResult(Optional<Book> book, Optional<Patron> patron) {
    }
}
