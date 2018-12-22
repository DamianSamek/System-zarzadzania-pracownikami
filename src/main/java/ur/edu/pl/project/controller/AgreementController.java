package ur.edu.pl.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ur.edu.pl.project.exceptions.AgreementApiException;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.Agreement;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.dto.AgreementWithEmployeeEmailDto;
import ur.edu.pl.project.services.AgreementService;
import ur.edu.pl.project.services.EmployeeService;

@RepositoryRestController
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @RequestMapping(method = RequestMethod.POST, value = "/agreement")
    public ResponseEntity<?> createAgreement(@RequestBody AgreementWithEmployeeEmailDto agreement) throws ApiException {
        return new ResponseEntity<>(agreementService.createAgreement(agreement), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PATCH, value="/agreement/{id}")
    public ResponseEntity<?> deleteAgreement(@PathVariable int id) throws AgreementApiException {
        agreementService.deleteAgreement(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
