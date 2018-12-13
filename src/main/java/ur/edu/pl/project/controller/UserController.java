package ur.edu.pl.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.repositories.UserRepository;
import ur.edu.pl.project.services.UserService;

@RepositoryRestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;


//	@RequestMapping(method = RequestMethod.PATCH, value = "/users/account/changepassword")
//	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePassword, HttpServletRequest request,
//			HttpServletResponse response) throws IOException, APIAuthenticationException {
//
//		User user = userService.changePassword(changePassword);
//		TokenAuthenticationService.addAuthentication(response, user.getEmail(), user.getRole().getRole());
//
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}


	//@PreAuthorize("hasRole('ROLE_APP_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/user/createuser")
	public ResponseEntity<?> createUser(@RequestBody User user) throws UserCreateException {
		userService.createUser(user);

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

//	//@PreAuthorize("hasRole('ROLE_APP_ADMIN')") // throws unhandled exception with code 403
//	@RequestMapping(method = RequestMethod.PATCH, value = "/users/modifyuserforadmin")
//	public ResponseEntity<?> modifyUserForAdmin(@RequestBody User user) throws UserCreateException {
//
//		userService.modifyUserForAdmin(user);
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}

//	@RequestMapping(method = RequestMethod.PATCH, value = "/users/account/update")
//	public ResponseEntity<?> profileUpdate(@RequestBody User user) throws APIAuthenticationException, UserCreateException {
//
//		if (authService.currentUser().getId() == user.getId())
//			userService.modifyUser(user);
//		else {
//			throw new APIAuthenticationException(ErrorResponseCodes.Authentication.UNATHORIZED_ERROR,
//					HttpStatus.UNAUTHORIZED, "Niedozwolona operacja.");
//		}
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}
//
//	@PreAuthorize("hasRole('ROLE_APP_ADMIN')")
//	@RequestMapping(method = RequestMethod.PATCH, value = "/users/account/adminchangepassword")
//	public ResponseEntity<?> adminChangePassword(@RequestBody ChangePasswordDTO adminChangePassword,
//			HttpServletRequest request, HttpServletResponse response)
//			throws APIAuthenticationException, IOException, UserCreateException {
//		User user = userService.adminChangePassword(adminChangePassword);
//		TokenAuthenticationService.addAuthentication(response, user.getEmail(), user.getRole().getRole());
//
//		return new ResponseEntity<>(user, HttpStatus.OK);
//	}
//
//	@PreAuthorize("hasRole('ROLE_APP_ADMIN')")
//	@RequestMapping(method = RequestMethod.POST, value = "/users/account/resetadmin")
//	public ResponseEntity<?> resetPassAdmin(@RequestBody User user) throws UserCreateException {
//		userService.resetPasswordAdmin(user);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
//
//	@PreAuthorize("hasRole('ROLE_APP_ADMIN')")
//	@RequestMapping(method = RequestMethod.POST, value = "/users/createserviceworker")
//	public ResponseEntity<?> createServiceWorker(@RequestBody User user) throws UserCreateException {
//		userService.createServiceWorker(user);
//
//		return new ResponseEntity<>(user, HttpStatus.CREATED);
//	}

}
