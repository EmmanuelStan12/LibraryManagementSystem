package com.bytebard.librarymanagementsystem.mappers;

public interface Mapper<Model, DTO> {

    DTO convertToDTO(Model model);

    Model convertToModel(DTO dto);
}
