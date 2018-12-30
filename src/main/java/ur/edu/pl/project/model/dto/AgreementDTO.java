package ur.edu.pl.project.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import ur.edu.pl.project.model.Agreement;

import lombok.Data;

@Data
@AllArgsConstructor
public class AgreementDTO {

    private int id;
    private String number;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateTo;
    private int salary;

    public AgreementDTO(Agreement agreement) {
        this.id = agreement.getId();
        this.number = agreement.getNumber();
        this.dateFrom = agreement.getDateFrom();
        this.dateTo = agreement.getDateTo();
        this.salary = agreement.getSalary();
    }
}