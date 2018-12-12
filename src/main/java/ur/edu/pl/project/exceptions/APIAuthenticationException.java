package ur.edu.pl.project.exceptions;

import org.springframework.http.HttpStatus;

public class APIAuthenticationException extends ApiException {

    private static final long serialVersionUID = 1L;

	public APIAuthenticationException(String errorCode, HttpStatus httpStatus, String errorDescription) {
		super(errorCode, httpStatus, errorDescription);
	}  
}