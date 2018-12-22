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
import ur.edu.pl.project.model.RaiseRequest;
import ur.edu.pl.project.model.dto.RaiseRequestDTO;
import ur.edu.pl.project.services.RaiseRequestService;

@RepositoryRestController
public class RaiseRequestController {

    @Autowired
    private RaiseRequestService raiseRequestService;

    @RequestMapping(method = RequestMethod.PATCH, value="/raiserequest/{id}")
    public ResponseEntity<?> considerRaiseRequest(@PathVariable int id, @RequestBody RaiseRequest raiseRequest) throws ApiException {
        raiseRequestService.considerRaiseRequest(id, raiseRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value="/raiserequest")
    public ResponseEntity<?> createRaiseRequest(@RequestBody RaiseRequestDTO raiseRequest) throws ApiException {
        raiseRequestService.createRaiseRequest(raiseRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
