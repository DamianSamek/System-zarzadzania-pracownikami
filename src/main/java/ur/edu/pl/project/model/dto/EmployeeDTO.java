package ur.edu.pl.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {
    private String firstName;
    private String secondName;
    private String email;
    private String phone;
    private String position;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String state;
    private String password;
    private String confirmPassword;
}
