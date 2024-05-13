package com.bytebard.librarymanagementsystem.exception_handlers;

import com.bytebard.librarymanagementsystem.dtos.ApiErrorResponse;
import com.bytebard.librarymanagementsystem.exceptions.AlreadyExistsException;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
            HttpServletRequest request, MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({NotFoundException.class, UsernameNotFoundException.class, AlreadyExistsException.class})
    public ResponseEntity<ApiErrorResponse> handleNotFoundExceptions(
            HttpServletRequest request, Exception ex) {
        HttpStatus status = ex instanceof AlreadyExistsException ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND;
        ApiErrorResponse response = new ApiErrorResponse(status.value(), ex.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}

