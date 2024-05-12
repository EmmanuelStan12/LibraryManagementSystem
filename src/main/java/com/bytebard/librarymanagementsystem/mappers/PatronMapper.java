package com.bytebard.librarymanagementsystem.mappers;

import com.bytebard.librarymanagementsystem.dtos.patron.CreatePatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.PatronDTO;
import com.bytebard.librarymanagementsystem.dtos.patron.UpdatePatronDTO;
import com.bytebard.librarymanagementsystem.models.Patron;
import com.bytebard.librarymanagementsystem.utils.Extensions;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PatronMapper implements Mapper<Patron, PatronDTO> {

    @Override
    public PatronDTO convertToDTO(Patron patron) {
        return new PatronDTO();
    }

    @Override
    public Patron convertToModel(PatronDTO patronDTO) {
        return new Patron();
    }

    public Patron convertToModel(CreatePatronDTO patronDTO) {
        return new Patron();
    }

    public Patron copyDTOToModel(UpdatePatronDTO src, Patron dest) {
        Extensions.executeIfNotBlank(src.getFirstName(), dest::setFirstName);
        Extensions.executeIfNotBlank(src.getLastName(), dest::setLastName);
        Extensions.executeIfNotBlank(src.getEmail(), dest::setEmail);
        Extensions.executeIfNotBlank(src.getPhone(), dest::setPhone);
        return dest;
    }
}
