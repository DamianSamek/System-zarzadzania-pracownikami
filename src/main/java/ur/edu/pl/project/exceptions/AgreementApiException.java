package ur.edu.pl.project.exceptions;

import org.springframework.http.HttpStatus;

public class AgreementApiException extends ApiException {

	private static final long serialVersionUID = -1579497105291737813L;

	public AgreementApiException(String errorCode, HttpStatus httpStatus, String errorDescription) {
		super(errorCode, httpStatus, errorDescription);
	}
}
