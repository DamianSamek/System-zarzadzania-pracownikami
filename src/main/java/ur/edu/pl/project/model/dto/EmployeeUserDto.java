package ur.edu.pl.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeUserDto {

    private int id;
    private String firstName;
    private String secondName;
    private String email;
    private List<ProjectDTO> projects;

}