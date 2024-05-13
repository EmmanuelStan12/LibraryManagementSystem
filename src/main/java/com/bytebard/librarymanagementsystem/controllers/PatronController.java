package com.bytebard.librarymanagementsystem.controllers;

import com.bytebard.librarymanagementsystem.dtos.ApiResponse;
import com.bytebard.librarymanagementsystem.dtos.patron.CreatePatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.PatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.UpdatePatronDTO;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.models.Patron;
import com.bytebard.librarymanagementsystem.services.PatronService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    public PatronController(final PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping
    @CachePut("patrons")
    public ResponseEntity<ApiResponse<List<PatronDTO>>> getAll() {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), null, patronService.getAllPatrons()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatronDTO>> getPatronById(@PathVariable final Long id) throws NotFoundException {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), null, patronService.getPatronById(id)),
                HttpStatus.OK
        );
    }

    @PostMapping
    @CacheEvict(value = "patrons", allEntries = true)
    public ResponseEntity<ApiResponse<PatronDTO>> createPatron(@Valid @RequestBody final CreatePatronDTO createPatronDTO) {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), null, patronService.createPatron(createPatronDTO)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "patrons", allEntries = true)
    public ResponseEntity<ApiResponse<PatronDTO>> updatePatron(@PathVariable final Long id, @RequestBody final UpdatePatronDTO updatePatronDTO) throws NotFoundException {
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), null, patronService.updatePatron(updatePatronDTO, id)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "patrons", allEntries = true)
    public ResponseEntity<ApiResponse<PatronDTO>> deletePatron(@PathVariable final Long id) throws NotFoundException {
        patronService.deletePatron(id);
        return new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "Patron deleted successfully", null),
                HttpStatus.OK
        );
    }
}
