package ur.edu.pl.project.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RaiseRequestDTO {

    private int agreementId;
    private int salaryRequest;
}