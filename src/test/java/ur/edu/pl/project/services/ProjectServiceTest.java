package ur.edu.pl.project.services;

import org.junit.Before;
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
import ur.edu.pl.project.model.Project;
import ur.edu.pl.project.model.dto.ProjectDTO;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    public ProjectDTO projectDTO;
    @Mock
    public ProjectRepository projectRepositoryMock;

    @Mock
    public EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    public ProjectService projectService;



    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createProject_IfEmployeeDoesntExistShouldThrowAnException() {

        projectDTO = new ProjectDTO();
        projectDTO.setEmployees(new ArrayList<>());
        projectDTO.getEmployees().add("Janusz Kukułka");

        when(employeeRepositoryMock.findByUserEmail(any())).thenReturn(null);
        assertThrows(ApiException.class, () -> {
            for(String employee: projectDTO.getEmployees()) {
                Employee emp = employeeRepositoryMock.findByUserEmail(employee);
                if(emp==null) throw new ApiException("Błąd przy dodawaniu projektu", HttpStatus.BAD_REQUEST,"Pracownik nie istnieje");
            }
        }
    );
}


    @Test
    public void updateProject_IfProjectNotExistShouldThrowAnException() {

        when(projectRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            Project existingProject = projectRepositoryMock.findById(0)
                    .orElseThrow(() -> new ApiException("Błąd przy edycji projektu",
                            HttpStatus.BAD_REQUEST,"Nie znaleziono projektu"));
        });
    }

    @Test
    public void deleteProject_IfProjectNotExistShouldThrowAnException() {
        when(projectRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            Project existingProject = projectRepositoryMock.findById(0)
                    .orElseThrow(() -> new ApiException("Błąd przy usuwaniu projektu"
                            ,HttpStatus.BAD_REQUEST,"Nie znaleziono projektu"));
        });
    }

    @Test
    public void getProjects_IfEmployeeNotExistShouldThrowAnException() {
        when(employeeRepositoryMock.findByUserId(0)).thenReturn(null);
        assertThrows(ApiException.class, () -> {
            Employee employee = employeeRepositoryMock.findByUserId(0);
            if(employee==null) throw new ApiException(
                    "Błąd przy pobieraniu projektów",HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");
        });
    }

    @Test
    public void getProject_IfProjectNotExistShouldThrowAnException() {
        when(projectRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> {
            Project project = projectRepositoryMock.findById(0).orElseThrow(
                    () -> new ApiException("Błąd przy pobieraniu projektu",
                            HttpStatus.BAD_REQUEST, "Nie znaleziono projektu"
                    ));
        });
    }
}