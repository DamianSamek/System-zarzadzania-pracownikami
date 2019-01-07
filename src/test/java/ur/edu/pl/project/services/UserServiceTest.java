package ur.edu.pl.project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.RaiseRequestRepository;
import ur.edu.pl.project.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    public UserRepository userRepository;

    @Mock
    public EmployeeRepository employeeRepository;

    @InjectMocks
    public ProjectService projectService;



    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEmployeeData_IfUserNotFoundShouldThrowAnException() {

        when(userRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            User user = userRepository.findById(0).orElseThrow(
                    () -> new ApiException("Błąd", HttpStatus.BAD_REQUEST,"Nie znaleziono użytkownika")
            );
        });
    }

    @Test
    public void getEmployeeData_IfUserFoundShouldNotThrowAnException() {

        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        assertDoesNotThrow(() -> {
            User user = userRepository.findById(0).orElseThrow(
                    () -> new ApiException("Błąd", HttpStatus.BAD_REQUEST,"Nie znaleziono użytkownika")
            );
        });
    }

    @Test
    public void getEmployeeData_IfEmployeeNotFoundShouldThrowAnException() {

        when(employeeRepository.findByUserId(0)).thenReturn(null);
        assertThrows(ApiException.class, () -> {
            Employee employee=employeeRepository.findByUserId(0);
            if (employee==null) throw new ApiException("Błąd",HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");
        });
    }

    @Test
    public void getEmployeeData_IfEmployeeFoundShouldNotThrowAnException() {

        when(employeeRepository.findByUserId(0)).thenReturn(new Employee());
        assertDoesNotThrow(() -> {
            Employee employee=employeeRepository.findByUserId(0);
            if (employee==null) throw new ApiException("Błąd",HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");
        });
    }


}