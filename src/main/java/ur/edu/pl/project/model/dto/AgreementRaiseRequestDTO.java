package ur.edu.pl.project.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import ur.edu.pl.project.model.Agreement;

import lombok.Data;
import ur.edu.pl.project.model.RaiseRequest;

@Data
@AllArgsConstructor
public class AgreementRaiseRequestDTO {

    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dateTo;
    private int salary;
    private boolean active;
    private List<RaiseRequestEmployeeDTO> raiseRequests;

    public AgreementRaiseRequestDTO(Agreement agreement) {
        this.id = agreement.getId();
        this.dateFrom = agreement.getDateFrom();
        this.dateTo = agreement.getDateTo();
        this.salary = agreement.getSalary();
        this.active = agreement.isActive();

        List<RaiseRequestEmployeeDTO> raiseRequests = new ArrayList<>();

        for (RaiseRequest request : agreement.getRaiseRequests()) {
         RaiseRequestEmployeeDTO dto = new RaiseRequestEmployeeDTO(request.getSalaryRequest(), request.isConsidered(), request.isAccepted());
        raiseRequests.add(dto);
        }
        this.raiseRequests = raiseRequests;
    }
}