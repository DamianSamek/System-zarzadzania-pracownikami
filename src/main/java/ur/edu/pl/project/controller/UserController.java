package ur.edu.pl.project.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ur.edu.pl.project.exceptions.APIAuthenticationException;
import ur.edu.pl.project.exceptions.ErrorResponseCodes;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.model.dto.ChangePasswordDTO;
import ur.edu.pl.project.security.TokenAuthenticationService;
import ur.edu.pl.project.services.AuthService;
import ur.edu.pl.project.services.UserService;


@RepositoryRestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;

	@RequestMapping(method = RequestMethod.POST, value = "/users/account/reset")
	public ResponseEntity<?> resetPassword(@RequestBody User user) throws UserCreateException {
		userService.createResetPasswordToken(user);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users/account/validate/{token}")
	public ResponseEntity<?> validateToken(@PathVariable("token") String token) {

		return new ResponseEntity<>(userService.validateToken(token), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/users/account/changepassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePassword, HttpServletRequest request,
											HttpServletResponse response) throws IOException, APIAuthenticationException {

		User user = userService.changePassword(changePassword);
		TokenAuthenticationService.addAuthentication(response, user.getEmail(), user.getRole().getRole());

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_APP_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/users/createuser")
	public ResponseEntity<?> createUser(@RequestBody User user) throws UserCreateException {
		userService.createUser(user);

		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_APP_ADMIN')") // throws unhandled exception with code 403
	@RequestMapping(method = RequestMethod.PATCH, value = "/users/modifyuserforadmin")
	public ResponseEntity<?> modifyUserForAdmin(@RequestBody User user) throws UserCreateException {

		userService.modifyUserForAdmin(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/users/account/update")
	public ResponseEntity<?> profileUpdate(@RequestBody User user) throws APIAuthenticationException, UserCreateException {

		if (authService.currentUser().getId() == user.getId())
			userService.modifyUser(user);
		else {
			throw new APIAuthenticationException(ErrorResponseCodes.Authentication.UNATHORIZED_ERROR,
					HttpStatus.UNAUTHORIZED, "Niedozwolona operacja.");
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_APP_ADMIN')")
	@RequestMapping(method = RequestMethod.PATCH, value = "/users/account/adminchangepassword")
	public ResponseEntity<?> adminChangePassword(@RequestBody ChangePasswordDTO adminChangePassword,
												 HttpServletRequest request, HttpServletResponse response)
			throws APIAuthenticationException, IOException, UserCreateException {
		User user = userService.adminChangePassword(adminChangePassword);
		TokenAuthenticationService.addAuthentication(response, user.getEmail(), user.getRole().getRole());

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}