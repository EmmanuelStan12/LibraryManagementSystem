package com.bytebard.librarymanagementsystem.services;

import com.bytebard.librarymanagementsystem.dtos.loan.LoanDTO;
import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.models.Loan;

public interface LoanService {

    LoanDTO loanBook(Long bookId, Long patronId) throws NotFoundException, AlreadyExistsException;

    LoanDTO returnBook(Long bookId, Long patronId) throws NotFoundException;
}
