package com.bytebard.librarymanagementsystem.exception_handlers;

import com.bytebard.librarymanagementsystem.dtos.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultAuthenticationExceptionHandler {

    @ExceptionHandler(value = {AuthenticationException.class, AuthenticationCredentialsNotFoundException.class})
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        String message = ex.getMessage();
        if (ex instanceof AuthenticationCredentialsNotFoundException) {
            message = "Invalid JWT Token or Authentication Credentials";
        }
        ApiErrorResponse restError = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restError);
    }
}
