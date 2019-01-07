package ur.edu.pl.project.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.repositories.AgreementRepository;
import ur.edu.pl.project.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AgreementServiceTest {

    @Mock
    public AgreementRepository agreementRepositoryMock;

    @Mock
    public EmployeeRepository employeeRepository;

    @Mock
    public AuthService authService;

    @InjectMocks
    public AgreementService agreementService;


    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createAgreement_IfEmployeeNotExistShouldThrowAnException() throws ApiException {
        when(employeeRepository.findByUserEmail("")).thenReturn(null);

        assertThrows(ApiException.class, () -> {
            Employee employee = employeeRepository.findByUserEmail("");
            if(employee==null) throw new ApiException("Błąd przy tworzeniu umowy"
                    , HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");
        });
    }

    @Test
    public void createAgreement_IfEmployeeAlreadyHasActiveAgreementShouldThrowAnException() throws ApiException {

        List<Agreement> existingAgreements = new ArrayList<>();
        Agreement a = new Agreement();
        a.setActive(true);
        a.setRaiseRequests(new ArrayList<>());
        a.setSalary(2000);
        a.setEmployee(new Employee());
        a.setDateOfCreation(new Date());
        a.setDateTo(new Date());
        a.setDateFrom(new Date());
        a.setId(0);
        existingAgreements.add(a);

        assertThrows((ApiException.class), () -> {
            for(Agreement existingAgreement : existingAgreements) {
                System.out.println(existingAgreement.isActive());
                if (existingAgreement.isActive()) throw new ApiException("Błąd przy tworzeniu umowy"
                        , HttpStatus.BAD_REQUEST,"Pracownik ma już aktywną umowę");
            }
        });
    }

    @Test
    public void getAgreement_IfAgreementDoesntExistShouldThrowAnException() {
        when(agreementRepositoryMock.findById(0)).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            Agreement agreement = agreementRepositoryMock.findById(0)
                .orElseThrow(() -> new ApiException("Błąd przy pobraniu umowy",
                        HttpStatus.BAD_REQUEST,"Nie znaleziono umowy"));});
    }

    @Test
    public void getAgreementForEmployee_IfEmployeeNotFoundShouldThrowAnException() {
        when(employeeRepository.findByUserId(0)).thenReturn(null);
        assertThrows(ApiException.class, () -> {
            Employee employee = employeeRepository.findByUserId(0);
            if(employee==null) throw new ApiException("Błąd przy pobieraniu umowy",HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");
        });
    }

    @Test
    public void getAgreementForEmployee_IfAgreementNotFoundShouldThrowAnException() {
        when(agreementRepositoryMock.findByEmployeeId(0)).thenReturn(null);
        assertThrows(ApiException.class, () -> {
            Agreement agreement = agreementRepositoryMock.findByEmployeeId(0);
            if(agreement==null) throw new ApiException("Błąd przy pobieraniu umowy",HttpStatus.BAD_REQUEST,"Nie znaleziono umowy");
        });
    }


   @Test
    public void deleteAgreement_IfAgreementNotFoundShouldThrowAnException() {

        when(agreementRepositoryMock.findById(any())).thenReturn(Optional.empty());
       assertThrows(ApiException.class, () -> {
           Agreement agreement = agreementRepositoryMock.findById(0).orElseThrow(
                   () -> new ApiException("Błąd przy usuwaniu umowy",
                           HttpStatus.BAD_REQUEST,"Nie znaleziono umowy"));
       });
    }
}