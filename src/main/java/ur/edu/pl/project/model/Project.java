package ur.edu.pl.project.model;

import com.fasterxml.jackson.annotation.*;
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
@Table(name="project")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Size(max=45)
    private String client;
    @Size(max=250)
    private String description;



    private int fee;

    private boolean finished;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable (
            name="employee_project",
            joinColumns = @JoinColumn(name="id_project", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="id_employee", referencedColumnName = "id")
    )
    List<Employee> employees;

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.getProjects().remove(this);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.getProjects().add(this);
    }

}
