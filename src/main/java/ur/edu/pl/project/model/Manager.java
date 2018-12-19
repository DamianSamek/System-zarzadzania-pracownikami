package ur.edu.pl.project.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="manager")
@Data

public class Manager {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
}