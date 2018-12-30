package ur.edu.pl.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ur.edu.pl.project.services.AuthService;
import ur.edu.pl.project.services.UserService;


@RepositoryRestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/user/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getEmployeeData(@PathVariable("id") int id){
		return new ResponseEntity<>(userService.getEmployeeData(id),HttpStatus.OK);
	}


}