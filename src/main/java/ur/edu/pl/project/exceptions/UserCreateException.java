package ur.edu.pl.project.exceptions;

import org.springframework.http.HttpStatus;

public class UserCreateException extends ApiException {
    
    private static final long serialVersionUID = 1L;

	public UserCreateException(String errorCode, HttpStatus httpStatus, String errorDescription) {
		super(errorCode, httpStatus, errorDescription);
	}

}