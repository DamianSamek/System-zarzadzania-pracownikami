package ur.edu.pl.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

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
    @Size(max=45)
    private String phone;
    @Size(max=45)
    private String position;
    @Size(max=45)
    private String streetAddress;
    @Size(max=256)
    private String postalCode;
    @Size(max=45)
    private String state;
    @Size(max=45)
    private String city;
    private boolean enabled;

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Transient
    @JsonProperty(access=Access.WRITE_ONLY)
    @Size(max = 65)
    private String firstName;
    @Transient
    @JsonProperty(access=Access.WRITE_ONLY)
    @Size(max = 65)
    private String secondName;
    @Transient
    @JsonProperty(access=Access.WRITE_ONLY)
    @Email
    @Size(max = 65)
    private String email;


//    List<Project> projects;

    @Transient
    @OneToMany(mappedBy = "employee")
    List<Agreement> agreements;

    @Transient
    @OneToMany(mappedBy = "employee")
    List<RaiseRequest> raiseRequests;
}
