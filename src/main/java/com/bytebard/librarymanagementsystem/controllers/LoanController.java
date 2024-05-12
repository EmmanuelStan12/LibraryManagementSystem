package com.bytebard.librarymanagementsystem.controllers;

import com.bytebard.librarymanagementsystem.dtos.ApiResponse;
import com.bytebard.librarymanagementsystem.dtos.loan.LoanDTO;
import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class LoanController {

    private final LoanService loanService;

    public LoanController(final LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/api/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<LoanDTO>> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) throws AlreadyExistsException, NotFoundException {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), null, loanService.loanBook(bookId, patronId)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/api/return/{bookId}/patron/{patronId}")
    public ResponseEntity<ApiResponse<LoanDTO>> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) throws NotFoundException {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), null, loanService.returnBook(bookId, patronId)),
                HttpStatus.OK
        );
    }
}
