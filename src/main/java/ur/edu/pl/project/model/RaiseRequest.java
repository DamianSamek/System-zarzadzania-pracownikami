package ur.edu.pl.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name="raise_request")
@Data
public class RaiseRequest {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="id_employee")
    public Employee employee;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="id_agreement")
    public Agreement agreement;

    private int salaryRequest;

    private boolean accepted;


}