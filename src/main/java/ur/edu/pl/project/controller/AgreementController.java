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
import ur.edu.pl.project.model.dto.AgreementWithEmployeeEmailDto;
import ur.edu.pl.project.services.AgreementService;
import ur.edu.pl.project.services.AuthService;

@RepositoryRestController
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @Autowired
    private AuthService authService;

    @RequestMapping(method = RequestMethod.POST, value = "/agreement")
    public ResponseEntity<?> createAgreement(@RequestBody AgreementWithEmployeeEmailDto agreement) throws ApiException {
        agreementService.createAgreement(agreement);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/agreement/{id}")
    public ResponseEntity<?> getAgreements(@PathVariable("id") int id) throws ApiException{

        if(authService.currentUser().getRole().getRole().equals("ROLE_MANAGER"))
            return new ResponseEntity<>(agreementService.getAgreement(id), HttpStatus.OK);
        else if(authService.currentUser().getRole().getRole().equals("ROLE_EMPLOYEE"))
            return new ResponseEntity<>(agreementService.getAgreementForEmployee(id), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/agreement/{id}")
    public ResponseEntity<?> deleteAgreement(@PathVariable int id) throws ApiException {
        agreementService.deleteAgreement(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
