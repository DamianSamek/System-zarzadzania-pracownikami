package ur.edu.pl.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="agreement")
@Data
public class Agreement {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Size(max=45)
    private String number;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date dateTo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfCreation;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name="id_employee")
    public Employee employee;

    private int salary;

    private boolean active;



//    List<Project> projects;

}
