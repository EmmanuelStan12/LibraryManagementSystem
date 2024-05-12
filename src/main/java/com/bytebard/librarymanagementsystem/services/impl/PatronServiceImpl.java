package com.bytebard.librarymanagementsystem.services.impl;

import com.bytebard.librarymanagementsystem.dtos.patron.CreatePatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.PatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.UpdatePatronDTO;
import com.bytebard.librarymanagementsystem.exceptions.NotFoundException;
import com.bytebard.librarymanagementsystem.mappers.PatronMapper;
import com.bytebard.librarymanagementsystem.models.Patron;
import com.bytebard.librarymanagementsystem.repository.PatronRepository;
import com.bytebard.librarymanagementsystem.services.PatronService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;

    private final PatronMapper patronMapper;

    public PatronServiceImpl(final PatronRepository patronRepository, PatronMapper patronMapper) {
        this.patronRepository = patronRepository;
        this.patronMapper = patronMapper;
    }

    @Override
    public List<PatronDTO> getAllPatrons() {
        return patronRepository.findAll().stream().map(patronMapper::convertToDTO).toList();
    }

    @Override
    public PatronDTO getPatronById(Long id) throws NotFoundException {
        Optional<Patron> patron = patronRepository.findById(id);
        if (patron.isEmpty()) {
            throw new NotFoundException("Patron with id " + id + " not found");
        }
        return patronMapper.convertToDTO(patron.get());
    }

    @Override
    public PatronDTO createPatron(CreatePatronDTO patronDTO) {
        try {
            Patron patron = patronMapper.convertToModel(patronDTO);
            patron = patronRepository.save(patron);
            return patronMapper.convertToDTO(patron);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Patron validation error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public PatronDTO updatePatron(UpdatePatronDTO patronDTO, Long patronId) throws NotFoundException {
        Optional<Patron> patronOptional = patronRepository.findById(patronId);
        if (patronOptional.isEmpty()) {
            throw new NotFoundException("Patron with id " + patronId + " not found");
        }
        Patron patron = patronOptional.get();
        patron = patronMapper.copyDTOToModel(patronDTO, patron);
        patron = patronRepository.save(patron);
        return patronMapper.convertToDTO(patron);
    }

    @Override
    public void deletePatron(Long id) throws NotFoundException {
        Optional<Patron> patronOptional = patronRepository.findById(id);
        if (patronOptional.isEmpty()) {
            throw new NotFoundException("Patron with id " + id + " not found");
        }
        patronRepository.deleteById(id);
    }
}
