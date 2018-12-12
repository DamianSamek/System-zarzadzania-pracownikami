package ur.edu.pl.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.services.EmployeeService;

@RepositoryRestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @RequestMapping(method = RequestMethod.POST, value = "/employee")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) throws UserCreateException {
        employeeService.createEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/employee")
    public ResponseEntity<?> modifyEmployee(@RequestBody Employee employee) throws UserCreateException {

        employeeService.modifyEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
