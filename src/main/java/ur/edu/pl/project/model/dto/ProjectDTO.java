package ur.edu.pl.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ur.edu.pl.project.model.Project;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProjectDTO {

    private int id;
    private String client;
    private String description;
    private int fee;
    private boolean finished;
    private List<String> employees;

    public ProjectDTO(Project project){
        this.id=project.getId();
        this.client = project.getClient();
        this.description = project.getDescription();
        this.fee=project.getFee();
        this.finished=project.isFinished();
    }

    public ProjectDTO() {}
}
