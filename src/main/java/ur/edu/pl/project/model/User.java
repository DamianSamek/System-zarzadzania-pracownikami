package ur.edu.pl.project.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;


@Entity
@Table(name="user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Size(max=45)
    private String firstName;
    @Size(max=45)
    private String secondName;
    @Email
    @NotNull
    @NotEmpty
    @Size(max=45)
    private String email;
    @NotNull
    @NotEmpty
    @JsonProperty(access=Access.WRITE_ONLY)
    @Size(max=256)
    private String password;
    private boolean enabled;
    @NotNull
    @ManyToOne
    @JoinColumn(name="id_role")
    public Role role;

    @Size(max=65)
    private String resetToken;

    private LocalDate validDate;
    @NotNull
    private boolean tokenValid;
}