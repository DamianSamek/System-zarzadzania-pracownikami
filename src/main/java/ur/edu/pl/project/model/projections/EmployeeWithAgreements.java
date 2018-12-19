package ur.edu.pl.project.model.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.dto.AgreementDTO;
import ur.edu.pl.project.model.dto.AgreementEmployeeDto;
import ur.edu.pl.project.model.dto.EmployeeUserDto;

import java.util.Date;
import java.util.List;

@Projection(name="employeeAgreements", types= {Employee.class})
public interface EmployeeWithAgreements {

    @Value("#{@employeeService.getAgreements(target)}")
    List<AgreementDTO> getAgreements();
}
