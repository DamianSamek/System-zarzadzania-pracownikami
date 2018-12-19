package ur.edu.pl.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AgreementWithEmployeeEmailDto {

    private String number;
    private Date dateFrom;
    private Date dateTo;
    private int salary;
    private String employeeEmail;
    }

