package ur.edu.pl.project.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.model.dto.EmployeeDTO;
import ur.edu.pl.project.repositories.UserRepository;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    EmployeeDTO employee;

    @Mock
    EmployeeService employeeService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        employee = new EmployeeDTO();
        employee.setState("Podkarpackie");
        employee.setPhone("725218446");
        employee.setStreetAddress("ul. Warszawska 4");
        employee.setPostalCode("38-222");
        employee.setPosition("Programista");
        employee.setCity("Rzeszow");
//        employee.setProjects(new ArrayList<>());
//        employee.setEnabled(true);
        employee.setFirstName("Marcin");
        employee.setSecondName("Kobylarz");
        employee.setEmail("kobylarz@firma.pl");
    }

    @Test
    public void createEmployee_employeeAlreadyExists() throws UserCreateException{

        Mockito.doThrow(UserCreateException.class).when(employeeService).createEmployee(employee);
        assertThrows(UserCreateException.class, () -> employeeService.createEmployee(employee));
    }
}