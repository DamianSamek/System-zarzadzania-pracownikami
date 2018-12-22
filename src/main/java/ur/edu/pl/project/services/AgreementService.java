package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.AgreementApiException;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.dto.AgreementEmployeeDto;
import ur.edu.pl.project.model.dto.AgreementWithEmployeeEmailDto;
import ur.edu.pl.project.repositories.AgreementRepository;
import ur.edu.pl.project.repositories.EmployeeRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AgreementService {

    @Autowired
    private final AgreementRepository agreementRepository;

    @Autowired
    private final EmployeeRepository employeeRepository;

    public AgreementService(AgreementRepository ar, EmployeeRepository er){
        this.agreementRepository=ar;
        this.employeeRepository=er;
    }

    public AgreementEmployeeDto getAgreementEmployeeDto(Agreement agreement) {
        Employee employeeFromAgreement = agreement.getEmployee();
        return new AgreementEmployeeDto(agreement.getId(), employeeFromAgreement.getUser().getFirstName(), employeeFromAgreement.getUser().getSecondName());
    }

    public Agreement createAgreement(AgreementWithEmployeeEmailDto agreementFromJSON) throws ApiException {

        Agreement existingAgreement = agreementRepository.findByNumber(agreementFromJSON.getNumber());
        Employee employee = employeeRepository.findByUserEmail(agreementFromJSON.getEmployeeEmail());
        Agreement agreement = new Agreement();
        if (existingAgreement==null && employee!=null) {

            agreement.setActive(true);
            agreement.setDateFrom(agreementFromJSON.getDateFrom());
            agreement.setDateTo(agreementFromJSON.getDateTo());
            agreement.setNumber(agreementFromJSON.getNumber());
            agreement.setSalary(agreementFromJSON.getSalary());
            agreement.setDateOfCreation(new Date());
            agreement.setEmployee(employee);
            employee.getAgreements().add(agreement);
            agreementRepository.save(agreement);
        }
        else throw new ApiException("lipa", HttpStatus.BAD_REQUEST,"Lipa");
        return agreement;
    }

    public void deleteAgreement(int id) throws AgreementApiException {

        Agreement agreement = agreementRepository.findById(id).get();
        if(agreement!=null) {
            agreement.setActive(false);
            agreementRepository.save(agreement);
        }
        else throw new AgreementApiException("401 BAD_REQUEST",HttpStatus.BAD_REQUEST,"Agreement not found");
    }
}
