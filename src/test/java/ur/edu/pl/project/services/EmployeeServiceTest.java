package ur.edu.pl.project.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.Role;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.model.dto.EmployeeDTO;
import ur.edu.pl.project.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    public EmployeeDTO employeeDTO;

    @Mock
    public EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    public EmployeeService employeeService;



    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createEmployee_IfEmployeeAlreadyExistsShouldThrowAnException() throws UserCreateException{
            when(employeeRepositoryMock.findByUserEmail("damian@firma.pl")).thenReturn(new Employee());
            assertThrows(UserCreateException.class, () -> {Employee existingEmployee = employeeRepositoryMock.findByUserEmail("damian@firma.pl");
                if (existingEmployee!=null)
                    throw new UserCreateException("Błąd w tworzeniu pracownika", HttpStatus.BAD_REQUEST,
                            "Pracownik o podanym emailu istnieje.");});
        }

        @Before
        public void set() {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setPassword("damian");
        employeeDTO.setConfirmPassword("damain");
        }
    @Test
    public void createEmployee_IfPasswordAndConfirmPasswordNotMatchShouldThrowAnException(){

        assertThrows(UserCreateException.class, () -> {
            if (employeeDTO.getPassword().equals(employeeDTO.getConfirmPassword())) {
                //newUser.setPassword(encryptionService.encode(employee.getPassword()));
            }
            else throw new UserCreateException("Błąd w tworzeniu pracownika",
                    HttpStatus.BAD_REQUEST,"Hasła nie zgadzają się");
        });
    }

    @Test
    public void modifyEmployee_IfEmployeeDoesntExistShouldThrowAnException() {

        when(employeeRepositoryMock.findById(0)).thenReturn(Optional.empty());
        assertThrows(UserCreateException.class, () -> {
            Employee existingEmployee = employeeRepositoryMock.findById(0)
                    .orElseThrow(() -> new UserCreateException("Błąd w edycji pracownika",
                            HttpStatus.BAD_REQUEST, "Pracownik nie istnieje."));
        });
    }

    @Test
    public void deleteEmployee_IfEmployeeDoesntExistShouldThrowAnException() {

        when(employeeRepositoryMock.findById(0)).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            Employee employee = employeeRepositoryMock.findById(0)
                    .orElseThrow(() -> new ApiException("Błąd przy usuwaniu pracownika"
                            ,HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika"));
        });
    }
    }
