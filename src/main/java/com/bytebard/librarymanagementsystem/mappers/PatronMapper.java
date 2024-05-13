package com.bytebard.librarymanagementsystem.mappers;

import com.bytebard.librarymanagementsystem.dtos.loan.LoanDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.CreatePatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.PatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.UpdatePatronDTO;
import com.bytebard.librarymanagementsystem.models.Loan;
import com.bytebard.librarymanagementsystem.models.Patron;
import com.bytebard.librarymanagementsystem.utils.Extensions;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatronMapper implements Mapper<Patron, PatronDTO> {

    @Override
    public PatronDTO convertToDTO(Patron patron) {
        return new PatronDTO(
                patron.getId(),
                patron.getFirstName(),
                patron.getLastName(),
                patron.getEmail(),
                patron.getPhone(),
                patron.getRegistrationDate()
        );
    }

    @Override
    public Patron convertToModel(PatronDTO patronDTO) {
        return new Patron(
                patronDTO.getId(),
                patronDTO.getFirstName(),
                patronDTO.getLastName(),
                patronDTO.getEmail(),
                patronDTO.getPhone(),
                patronDTO.getRegistrationDate(),
                null
        );
    }

    public Patron convertToModel(CreatePatronDTO patronDTO) {
        return new Patron(
                null,
                patronDTO.getFirstName(),
                patronDTO.getLastName(),
                patronDTO.getEmail(),
                patronDTO.getPhone(),
                LocalDate.now(),
                null
        );
    }

    public Patron copyDTOToModel(UpdatePatronDTO src, Patron dest) {
        Extensions.executeIfNotBlank(src.getFirstName(), dest::setFirstName);
        Extensions.executeIfNotBlank(src.getLastName(), dest::setLastName);
        Extensions.executeIfNotBlank(src.getEmail(), dest::setEmail);
        Extensions.executeIfNotBlank(src.getPhone(), dest::setPhone);
        return dest;
    }
}
