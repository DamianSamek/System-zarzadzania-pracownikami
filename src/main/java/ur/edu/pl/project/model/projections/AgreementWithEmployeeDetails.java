package ur.edu.pl.project.model.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.dto.AgreementEmployeeDto;
import ur.edu.pl.project.model.dto.EmployeeUserDto;

import java.util.Date;

@Projection(name="withEmployeeDetails", types= {Agreement.class})
public interface AgreementWithEmployeeDetails {

    int getId();
    Date getDateFrom();
    Date getDateTo();
    int getSalary();
    boolean isActive();


    @Value("#{@agreementService.getAgreementEmployeeDto(target).getFirstName()}")
    String getFirstName();

    @Value("#{@agreementService.getAgreementEmployeeDto(target).getSecondName()}")
    String getSecondName();

}
