package com.bytebard.librarymanagementsystem.exception_handlers;

import com.bytebard.librarymanagementsystem.dtos.ApiErrorResponse;
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
}
