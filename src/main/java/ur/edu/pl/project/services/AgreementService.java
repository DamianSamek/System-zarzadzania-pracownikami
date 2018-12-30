package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.AgreementApiException;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.Project;
import ur.edu.pl.project.model.dto.AgreementDTO;
import ur.edu.pl.project.model.dto.AgreementEmployeeDto;
import ur.edu.pl.project.model.dto.AgreementWithEmployeeEmailDto;
import ur.edu.pl.project.model.dto.ProjectDTO;
import ur.edu.pl.project.repositories.AgreementRepository;
import ur.edu.pl.project.repositories.EmployeeRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AgreementService {

    @Autowired
    private final AgreementRepository agreementRepository;

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final AuthService authService;

    public AgreementService(AuthService as, AgreementRepository ar, EmployeeRepository er){
        this.agreementRepository=ar;
        this.employeeRepository=er;
        this.authService=as;
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

            agreement.setDateFrom(agreementFromJSON.getDateFrom());
            agreement.setDateTo(agreementFromJSON.getDateTo());
            agreement.setNumber(agreementFromJSON.getNumber());
            agreement.setSalary(agreementFromJSON.getSalary());
            agreement.setDateOfCreation(new Date());
            agreement.setEmployee(employee);
            agreementRepository.save(agreement);
        }
        else throw new ApiException("lipa", HttpStatus.BAD_REQUEST,"Lipa");
        return agreement;
    }


    public AgreementDTO getAgreement(int id) {
        Agreement agreement = agreementRepository.findById(id).get();
        return new AgreementDTO(agreement);
    }

    public AgreementDTO getAgreementForEmployee(int id) throws ApiException{

        if(id==authService.currentUser().getId()) {
            Employee employee = employeeRepository.findByUserId(id);
            Agreement agreement = agreementRepository.findByEmployeeId(employee.getId());
            return new AgreementDTO(agreement);
        } else throw new ApiException("400",HttpStatus.BAD_REQUEST,"400");

    }

    public void deleteAgreement(int id) throws AgreementApiException {

        Agreement agreement = agreementRepository.findById(id).get();
        if(agreement!=null) {
            agreement.getEmployee().setAgreement(null);
            employeeRepository.save(agreement.getEmployee());
            agreementRepository.delete(agreement);
        }
        else throw new AgreementApiException("401 BAD_REQUEST",HttpStatus.BAD_REQUEST,"Agreement not found");
    }
}
