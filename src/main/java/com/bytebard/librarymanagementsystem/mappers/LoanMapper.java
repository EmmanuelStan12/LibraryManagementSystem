package com.bytebard.librarymanagementsystem.mappers;

import com.bytebard.librarymanagementsystem.dtos.loan.LoanDTO;
import com.bytebard.librarymanagementsystem.models.Loan;

public class LoanMapper implements Mapper<Loan, LoanDTO> {

    @Override
    public LoanDTO convertToDTO(Loan loan) {
        return new LoanDTO();
    }

    @Override
    public Loan convertToModel(LoanDTO loanDTO) {
        return null;
    }
}
