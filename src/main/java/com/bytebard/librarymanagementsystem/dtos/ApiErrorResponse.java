package com.bytebard.librarymanagementsystem.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApiErrorResponse {

    private HttpStatus statusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private List<String> errors;

    public ApiErrorResponse(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(HttpStatus statusCode, String message, List<String> errors) {
        this.statusCode = statusCode;
        this.message = message;
        timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}
