package ur.edu.pl.project.model.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.dto.EmployeeUserDto;

import java.util.List;

@Projection(name="withUserDetails", types= {Employee.class})
public interface EmployeeWithUserDetails {

    int getId();
    String getPhone();
    String getPosition();
    String getStreetAddress();
    String getState();
    String getPostalCode();
    String getCity();


    boolean isEnabled();

    @Value("#{@employeeService.getEmployeeUserDto(target).getFirstName()}")
    String getFirstName();

    @Value("#{@employeeService.getEmployeeUserDto(target).getSecondName()}")
    String getSecondName();

    @Value("#{@employeeService.getEmployeeUserDto(target).getEmail()}")
    String getEmail();

    List<Agreement> getAgreements();

}
