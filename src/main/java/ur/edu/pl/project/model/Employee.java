package ur.edu.pl.project.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name="employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Size(max=45)
    private String phone;
    @NotNull
    @Size(max=45)
    private String position;
    @NotNull
    @Size(max=45)
    private String streetAddress;
    @NotNull
    @Size(max=256)
    private String postalCode;
    @NotNull
    @Size(max=45)
    private String state;
    @NotNull
    @Size(max=45)
    private String city;


    @NotNull
    private boolean enabled;

    @NotNull
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    List<Agreement> agreements;

    @ManyToMany(mappedBy="employees", cascade = {CascadeType.ALL})
    List<Project> projects;

    @Transient
    @OneToMany(mappedBy = "employee")
    List<RaiseRequest> raiseRequests;

    public void removeProject(Project project) {
        projects.remove(project);
        project.getEmployees().remove(this);
    }

    public void addProject(Project project) {
        projects.add(project);
        project.getEmployees().add(this);
    }
}
