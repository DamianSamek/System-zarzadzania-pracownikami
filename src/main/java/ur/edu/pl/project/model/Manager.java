package ur.edu.pl.project.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="project")
@Data

public class Manager {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
}