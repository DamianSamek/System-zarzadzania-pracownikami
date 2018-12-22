package ur.edu.pl.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.Where;

@Entity
@Table(name="raise_request")
@Where(clause = "considered=0")
@Data
public class RaiseRequest {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name="id_employee")
    public Employee employee;

    @ManyToOne
    @JoinColumn(name="id_agreement")
    public Agreement agreement;

    private int salaryRequest;

    private boolean accepted;

    private boolean considered;


}