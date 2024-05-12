package com.bytebard.librarymanagementsystem.services;

import com.bytebard.librarymanagementsystem.dtos.patron.CreatePatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.PatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.UpdatePatronDTO;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.models.Patron;

import java.util.List;

public interface PatronService {

    List<PatronDTO> getAllPatrons();

    PatronDTO getPatronById(Long id) throws NotFoundException;

    PatronDTO createPatron(CreatePatronDTO patronDTO);

    PatronDTO updatePatron(UpdatePatronDTO patronDTO, Long patronId) throws NotFoundException;

    void deletePatron(Long id) throws NotFoundException;
}
