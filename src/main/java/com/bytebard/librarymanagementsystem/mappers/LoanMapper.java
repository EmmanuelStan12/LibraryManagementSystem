package com.bytebard.librarymanagementsystem.mappers;

import com.bytebard.librarymanagementsystem.dtos.loan.LoanDTO;
import com.bytebard.librarymanagementsystem.models.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper implements Mapper<Loan, LoanDTO> {

    @Override
    public LoanDTO convertToDTO(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                new PatronMapper().convertToDTO(loan.getPatron()),
                new BookMapper().convertToDTO(loan.getBook()),
                loan.getBorrowedDate(),
                loan.getReturnedDate()
        );
    }

    @Override
    public Loan convertToModel(LoanDTO loanDTO) {
        return new Loan(
                loanDTO.getId(),
                new PatronMapper().convertToModel(loanDTO.getPatron()),
                new BookMapper().convertToModel(loanDTO.getBook()),
                loanDTO.getBorrowedDate(),
                loanDTO.getReturnedDate()
        );
    }
}
