package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.Project;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.model.dto.EmployeeDTO;
import ur.edu.pl.project.model.dto.EmployeeUserDto;
import ur.edu.pl.project.model.dto.ProjectDTO;
import ur.edu.pl.project.model.enums.Roles;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.RoleRepository;
import ur.edu.pl.project.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final StringUtils stringUtils;
    private final EncryptionService encryptionService;
    private final AuthService authService;

    @Autowired
    public EmployeeService(EmployeeRepository eRepo, RoleRepository rRepo, StringUtils sUtils, EncryptionService es, AuthService auth)
    {
        this.employeeRepository=eRepo;
        this.roleRepository=rRepo;
        this.stringUtils = sUtils;
        this.encryptionService=es;
        this.authService=auth;
    }

    public void createEmployee(EmployeeDTO employee) throws UserCreateException {
        Employee existingEmployee = employeeRepository.findByUserEmail(employee.getEmail());

        if (existingEmployee!=null)
            throw new UserCreateException("Błąd w tworzeniu pracownika", HttpStatus.BAD_REQUEST,
                    "Pracownik o podanym emailu istnieje.");
        else {
            if (!stringUtils.isEmptyOrWhitespaceOnly(employee.getEmail())) {

                Employee newEmployee = new Employee();
                User newUser = new User();

                newUser.setRole(roleRepository.findByRole(Roles.ROLE_EMPLOYEE.toString()));
                newUser.setEmail(employee.getEmail());
                if (employee.getPassword().equals(employee.getConfirmPassword())) {
                    newUser.setPassword(encryptionService.encode(employee.getPassword()));
                }
                else throw new UserCreateException("Błąd w tworzeniu pracownika",
                        HttpStatus.BAD_REQUEST,"Hasła nie zgadzają się");

                newUser.setEnabled(true);
                newUser.setFirstName(employee.getFirstName());
                newUser.setSecondName(employee.getSecondName());
                newUser.setTokenValid(true);

                newEmployee.setEnabled(true);
                newEmployee.setStreetAddress(employee.getStreetAddress());
                newEmployee.setCity(employee.getCity());
                newEmployee.setPostalCode(employee.getPostalCode());
                newEmployee.setState(employee.getState());
                newEmployee.setPhone(employee.getPhone());
                newEmployee.setPosition(employee.getPosition());
                newEmployee.setUser(newUser);
                newEmployee.setProjects(new ArrayList<>());
                newEmployee.setAgreements(new ArrayList<>());
                employeeRepository.save(newEmployee);
            }
        }
    }


    public void modifyEmployee(int id, EmployeeDTO employee) throws UserCreateException {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new UserCreateException("Błąd w edycji pracownika",
                        HttpStatus.BAD_REQUEST, "Pracownik nie istnieje."));

        if	(employee.getPhone() != null)
            existingEmployee.setPhone(employee.getPhone());
        if (employee.getFirstName() != null)
            existingEmployee.getUser().setFirstName(employee.getFirstName());
        if (employee.getSecondName() != null)
            existingEmployee.getUser().setSecondName(employee.getSecondName());
        if (employee.getPostalCode() != null)
            existingEmployee.setPostalCode(employee.getPostalCode());
        if (employee.getStreetAddress() != null)
            existingEmployee.setStreetAddress(employee.getStreetAddress());
        if (employee.getCity() != null)
            existingEmployee.setCity(employee.getCity());
        if (employee.getState() != null)
            existingEmployee.setState(employee.getState());
        if (employee.getPosition() != null)
            existingEmployee.setPosition(employee.getPosition());
        if(employee.getEmail() != null)
            existingEmployee.getUser().setEmail(employee.getEmail());

        employeeRepository.save(existingEmployee);
    }

    public EmployeeUserDto getEmployeeUserDto(Employee employee) {
        User userFromEmployee = employee.getUser();

        List<ProjectDTO> projects = new ArrayList<>();
        for (Project p : employee.getProjects()) {
            projects.add(new ProjectDTO(p));
        }
        return new EmployeeUserDto(employee.getId(), userFromEmployee.getFirstName(), userFromEmployee.getSecondName(),
                userFromEmployee.getEmail(), projects);
    }

    public void deleteEmployee(int id) throws ApiException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ApiException("Błąd przy usuwaniu pracownika"
                        ,HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika"));

            employee.setEnabled(false);
            if(employee.getAgreements()!=null){
                for(Agreement a : employee.getAgreements()) a.setActive(false);
            }
            employeeRepository.save(employee);
    }


}
