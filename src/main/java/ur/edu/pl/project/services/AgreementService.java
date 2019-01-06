package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;

import ur.edu.pl.project.model.dto.*;
import ur.edu.pl.project.repositories.AgreementRepository;
import ur.edu.pl.project.repositories.EmployeeRepository;

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

    public void createAgreement(AgreementWithEmployeeEmailDto agreementFromJSON) throws ApiException {

        List<Agreement> existingAgreements = agreementRepository.findAllByEmployeeUserEmail(agreementFromJSON.getEmployeeEmail());
        Employee employee = employeeRepository.findByUserEmail(agreementFromJSON.getEmployeeEmail());
        Agreement agreement = new Agreement();

        if(employee==null) throw new ApiException("Błąd przy tworzeniu umowy"
                ,HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");

         else {
                for(Agreement existingAgreement : existingAgreements) {
                    if (existingAgreement.isActive()) throw new ApiException("Błąd przy tworzeniu umowy"
                            , HttpStatus.BAD_REQUEST,"Pracownik ma już aktywną umowę");
                }
            agreement.setDateFrom(agreementFromJSON.getDateFrom());
            agreement.setDateTo(agreementFromJSON.getDateTo());
            agreement.setSalary(agreementFromJSON.getSalary());
            agreement.setDateOfCreation(new Date());
            agreement.setEmployee(employee);
            agreement.getEmployee().getAgreements().add(agreement);
            agreement.setActive(true);
            agreementRepository.save(agreement);
        }
    }


    public AgreementDTO getAgreement(int id) throws ApiException {
        Agreement agreement = agreementRepository.findById(id).orElseThrow(() -> new ApiException("Błąd przy pobraniu umowy",HttpStatus.BAD_REQUEST,"Nie znaleziono umowy"));
        return new AgreementDTO(agreement);
    }

    public AgreementRaiseRequestDTO getAgreementForEmployee(int id) throws ApiException{

        if(id==authService.currentUser().getId()) {
            Employee employee = employeeRepository.findByUserId(id);
            if(employee==null) throw new ApiException("Błąd przy pobieraniu umowy",HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");
            Agreement agreement = agreementRepository.findByEmployeeId(employee.getId());
            if(agreement==null) throw new ApiException("Błąd przy pobieraniu umowy",HttpStatus.BAD_REQUEST,"Nie znaleziono umowy");
            return new AgreementRaiseRequestDTO(agreement);
        } else throw new ApiException("Błąd przy pobieraniu umowy",HttpStatus.UNAUTHORIZED,"Brak uprawnień");

    }

    public void deleteAgreement(int id) throws ApiException {

        Agreement agreement = agreementRepository.findById(id).orElseThrow(() -> new ApiException("Błąd przy usuwaniu umowy",HttpStatus.BAD_REQUEST,"Nie znaleziono umowy"));

            agreement.setActive(false);
            agreementRepository.save(agreement);

    }
}
