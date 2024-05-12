package com.bytebard.librarymanagementsystem.exception_handlers;

import com.bytebard.sharespace.shared.ApiErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestController
@RestControllerAdvice
@ResponseBody
public class DefaultAuthenticationExceptionHandler implements ErrorController {

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(Exception ex) {
        ApiErrorResponse restError = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restError);
    }

    @ExceptionHandler(value = {ServletException.class})
    public ResponseEntity<Object> handleServletException(Exception ex) {
        ApiErrorResponse restError = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(restError);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleGenericException(final Exception ex ,final HttpServletRequest request) {
        System.out.println("Class name: " + ex.getClass().getName());
        ApiErrorResponse restError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restError);
    }
}
