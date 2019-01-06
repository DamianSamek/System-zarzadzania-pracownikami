package ur.edu.pl.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name="raise_request")
@Data
public class RaiseRequest {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


    @NotNull
    @ManyToOne
    @JoinColumn(name="id_employee")
    public Employee employee;

    @NotNull
    @ManyToOne
    @JoinColumn(name="id_agreement")
    public Agreement agreement;

    @NotNull
    private int salaryRequest;

    @NotNull
    private boolean accepted;

    @NotNull
    private boolean considered;


}