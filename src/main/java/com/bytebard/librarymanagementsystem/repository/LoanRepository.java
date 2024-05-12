package com.bytebard.librarymanagementsystem.repository;

import com.bytebard.librarymanagementsystem.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT l FROM Loans l WHERE l.book.id = :bookId AND l.patron.id IS NOT NULL AND l.returnedDate IS NULL")
    Optional<Loan> findLoanByBookId(Long bookId);

    @Query("SELECT l FROM Loans l WHERE l.book.id = :bookId AND l.patron.id = :patronId AND l.returnedDate IS NULL")
    Optional<Loan> findLoanByBookIdAndPatronId(Long bookId, Long patronId);
}
