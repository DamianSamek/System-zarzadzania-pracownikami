package ur.edu.pl.project.exceptions;

import java.util.AbstractMap;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"httpStatus", "message", "errorDetails", "stackTrace", "cause", "suppressed", "localizedMessage"})
public class ApiException extends Exception {

    private static final long serialVersionUID = 1876819830060979152L;
    private final HttpStatus httpStatus;
    private final String errorDescription;
    private final String errorCode;
    private List<AbstractMap.SimpleEntry<String, String>> errorDetails;

    public ApiException(String errorCode, HttpStatus httpStatus, String errorDescription) {
        super();
        this.httpStatus = httpStatus;
        this.errorDescription = errorDescription;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s", httpStatus, errorDescription);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public List<AbstractMap.SimpleEntry<String, String>> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(List<AbstractMap.SimpleEntry<String, String>> errorDetails) {
        this.errorDetails = errorDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

}