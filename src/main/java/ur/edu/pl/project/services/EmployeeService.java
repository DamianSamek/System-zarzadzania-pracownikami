package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.ErrorResponseCodes;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.model.enums.Roles;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.RoleRepository;
import ur.edu.pl.project.utils.StringUtils;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final StringUtils stringUtils;

    @Autowired
    public EmployeeService(EmployeeRepository eRepo, RoleRepository rRepo, StringUtils sUtils)
    {
        this.employeeRepository=eRepo;
        this.roleRepository=rRepo;
        this.stringUtils = sUtils;
    }

    public void createEmployee(Employee employee) throws UserCreateException {
        Employee existingEmployee = employeeRepository.findByUserEmail(employee.getEmail());

        createUserForEmployee(employee, existingEmployee);
        employeeRepository.save(employee);
    }

    private void createUserForEmployee(Employee employee, Employee existingEmployee) throws UserCreateException {
        if (existingEmployee == null) {
            if (!stringUtils.isEmptyOrWhitespaceOnly(employee.getEmail())) {
                User newUser = new User();
                newUser.setRole(roleRepository.findByRole(Roles.ROLE_EMPLOYEE.toString()));
                newUser.setEmail(employee.getEmail());
                newUser.setPassword("damian");
                newUser.setEnabled(true);
                newUser.setFirstName(employee.getFirstName());
                newUser.setSecondName(employee.getSecondName());

                employee.setUser(newUser);
                //sendEmail("createCustomer", customer);
            }
        } else {
            throw new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR, HttpStatus.BAD_REQUEST,
                    "Pracownik o podanym emailu istnieje.");
        }
    }

    public void modifyEmployee(Employee employee) throws UserCreateException {

        Employee existingEmployee = findEmployeeById(employee);
        setFieldsForCustomer(employee, existingEmployee);


        employeeRepository.save(existingEmployee);
    }

    private Employee findEmployeeById(Employee employee) throws UserCreateException {
        Employee existingEmployee = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR,
                        HttpStatus.BAD_REQUEST, "Pracownik nie istnieje."));
        return existingEmployee;
    }

    private void setFieldsForCustomer(Employee employee, Employee existingEmployee) {
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
    }
}
