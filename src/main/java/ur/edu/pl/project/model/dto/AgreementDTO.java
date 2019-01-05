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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateTo;
    private int salary;
    private boolean active;

    public AgreementDTO(Agreement agreement) {
        this.id = agreement.getId();
        this.dateFrom = agreement.getDateFrom();
        this.dateTo = agreement.getDateTo();
        this.salary = agreement.getSalary();
        this.active = agreement.isActive();
    }
}