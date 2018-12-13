package ur.edu.pl.project.model.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.dto.EmployeeUserDto;

@Projection(name="withUserDetails", types= {Employee.class})
public interface EmployeeWithUserDetails {

    int getId();
    String getPhone();
    String getPosition();

    @Value("#{@employeeService.getEmployeeUserDto(target)}")
    EmployeeUserDto getUser();
}
