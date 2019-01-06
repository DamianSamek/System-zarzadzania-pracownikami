package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.RaiseRequest;
import ur.edu.pl.project.model.dto.AgreementDTO;
import ur.edu.pl.project.model.dto.RaiseRequestDTO;
import ur.edu.pl.project.repositories.AgreementRepository;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.RaiseRequestRepository;

@Service
public class RaiseRequestService {

    @Autowired
    private final RaiseRequestRepository raiseRequestRepository;

    @Autowired
    private final AgreementRepository agreementRepository;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final EmployeeRepository employeeRepository;

    public RaiseRequestService(EmployeeRepository er,RaiseRequestRepository rrr, AgreementRepository ar, AuthService as) {
        this.raiseRequestRepository = rrr;
        this.agreementRepository = ar;
        this.authService = as;
        this.employeeRepository = er;
    }

    public void createRaiseRequest(RaiseRequestDTO raiseRequestFromJSON) throws ApiException {

        Agreement agreement = agreementRepository.findById(raiseRequestFromJSON.getAgreementId())
                .orElseThrow(() -> new ApiException("Błąd przy tworzeniu zapytania o podwyżkę",
                        HttpStatus.BAD_REQUEST, "Nie znaleziono umowy"));
        Employee employee = employeeRepository.findByUserEmail(authService.currentUser().getEmail());
        if (employee==null) throw new ApiException("Błąd przy tworzeniu zapytania o podwyżkę",
                HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");


            RaiseRequest raiseRequest = new RaiseRequest();
            raiseRequest.setAgreement(agreement);
            raiseRequest.setEmployee(employee);
            raiseRequest.setConsidered(false);
            raiseRequest.setAccepted(false);
            raiseRequest.setSalaryRequest(raiseRequestFromJSON.getSalaryRequest());
            agreement.getRaiseRequests().add(raiseRequest);

            raiseRequestRepository.save(raiseRequest);
    }

    public AgreementDTO getRaiseRequestAgreementDto(RaiseRequest raiseRequest) {
        Agreement agreementFromRequest = raiseRequest.getAgreement();
        return new AgreementDTO(agreementFromRequest.getId(),
                 agreementFromRequest.getDateFrom(),
                agreementFromRequest.getDateTo(), agreementFromRequest.getSalary(), agreementFromRequest.isActive());
    }

    public void considerRaiseRequest(int id, RaiseRequest raiseRequestFromJSON) throws ApiException{
        RaiseRequest raiseRequest = raiseRequestRepository.findById(id).orElseThrow(() ->
                new ApiException("Błąd przy rozpatrzaniu zapytania",
                        HttpStatus.BAD_REQUEST,"Nie znaleziono zapytania"));

            raiseRequest.setConsidered(true);
            raiseRequest.setAccepted(raiseRequestFromJSON.isAccepted());
            if(raiseRequest.isAccepted()) {
                raiseRequest.getAgreement().setSalary(raiseRequest.getSalaryRequest());
            }
            agreementRepository.save(raiseRequest.getAgreement());
    }
}
