package ur.edu.pl.project.services;

import org.springframework.stereotype.Service;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.dto.AgreementEmployeeDto;

@Service
public class AgreementService {


    public AgreementEmployeeDto getAgreementEmployeeDto(Agreement agreement) {
        Employee employeeFromAgreement = agreement.getEmployee();
        return new AgreementEmployeeDto(agreement.getId(), employeeFromAgreement.getUser().getFirstName(), employeeFromAgreement.getUser().getSecondName());
    }
}
