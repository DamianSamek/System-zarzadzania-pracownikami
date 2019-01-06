package ur.edu.pl.project.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RaiseRequestEmployeeDTO {

    private int salaryRequest;
    private boolean considered;
    private boolean accepted;

}