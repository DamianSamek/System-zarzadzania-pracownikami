package ur.edu.pl.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.dto.EmployeeDTO;
import ur.edu.pl.project.services.EmployeeService;

@RepositoryRestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @RequestMapping(method = RequestMethod.POST, value = "/employee")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employee) throws UserCreateException {
        employeeService.createEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/employee/{id}")
    public ResponseEntity<?> modifyEmployee(@PathVariable("id") int id, @RequestBody EmployeeDTO employee) throws UserCreateException {

        employeeService.modifyEmployee(id, employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id) throws ApiException {

        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
