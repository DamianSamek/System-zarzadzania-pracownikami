package ur.edu.pl.project.model.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.RaiseRequest;

@Projection(name="withAgreementAndEmployee", types= {RaiseRequest.class})
public interface RaiseRequestWithAgreementAndEmployee {

    int getId();
    int getSalaryRequest();
    boolean isConsidered();
    boolean isAccepted();

    @Value("#{@employeeService.getEmployeeUserDto(target.getEmployee()).getFirstName()}")
    String getFirstName();

    @Value("#{@employeeService.getEmployeeUserDto(target.getEmployee()).getSecondName()}")
    String getSecondName();

    @Value("#{@raiseRequestService.getRaiseRequestAgreementDto(target).getId()}")
    int getAgreementId();

    @Value("#{@raiseRequestService.getRaiseRequestAgreementDto(target).isActive()}")
    boolean isActive();



}
