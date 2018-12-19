package ur.edu.pl.project.model.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {

    private String token;
    private String password;
    private String confirmPassword;
}