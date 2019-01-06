package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.ApiException;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.Project;
import ur.edu.pl.project.model.dto.ProjectDTO;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.ProjectRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final AuthService authService;


    public ProjectService(ProjectRepository pr, EmployeeRepository er, AuthService as) {
        this.projectRepository = pr;
        this.employeeRepository = er;
        this.authService = as;
    }
    public void createProject(ProjectDTO projectDTO) throws ApiException{

        Project project = new Project();
        project.setClient(projectDTO.getClient());
        project.setDescription(projectDTO.getDescription());
        project.setFinished(projectDTO.isFinished());
        project.setFee(projectDTO.getFee());
        project.setEmployees(new ArrayList<>());
        for(String employee: projectDTO.getEmployees()) {
            Employee emp = employeeRepository.findByUserEmail(employee);
            if(emp==null) throw new ApiException("Błąd przy dodawaniu projektu", HttpStatus.BAD_REQUEST,"Pracownik nie istnieje");
            else emp.addProject(project);
        }
        projectRepository.save(project);
    }

    public void updateProject(int id, ProjectDTO projectDTO) throws ApiException {

        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ApiException("Błąd przy edycji projektu",
                        HttpStatus.BAD_REQUEST,"Nie znaleziono projektu"));

        if(existingProject!=null) {
            if (projectDTO.getClient()!=null)
            existingProject.setClient(projectDTO.getClient());
            if (projectDTO.getFee()!=0)
            existingProject.setFee(projectDTO.getFee());
            if(projectDTO.isFinished()==true || projectDTO.isFinished()==false)
            existingProject.setFinished(projectDTO.isFinished());
            if(projectDTO.getDescription()!=null)
            existingProject.setDescription(projectDTO.getDescription());

            if(!projectDTO.getEmployees().isEmpty())
            {
            if(existingProject.getEmployees()!=null && !existingProject.getEmployees().isEmpty()) {

                while (existingProject.getEmployees().iterator().hasNext()) {
                    existingProject.getEmployees().iterator().next().removeProject(existingProject);
                }

                existingProject.getEmployees().clear();
            }
                for (String email : projectDTO.getEmployees()) {
                    Employee e = employeeRepository.findByUserEmail(email);
                    e.addProject(existingProject);

            }}

            projectRepository.save(existingProject);
        }

    }

    public void deleteProject(int id) throws ApiException{
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ApiException("Błąd przy usuwaniu projektu"
                        ,HttpStatus.BAD_REQUEST,"Nie znaleziono projektu"));

            while(existingProject.getEmployees().iterator().hasNext()) {
                existingProject.getEmployees().iterator().next().removeProject(existingProject);
            }
            projectRepository.delete(existingProject);
       }

    public List<ProjectDTO> getProjects(int id) throws ApiException{
        ArrayList<ProjectDTO> projectsDTO = new ArrayList<>();
        if (id==authService.currentUser().getId()){
            Employee employee = employeeRepository.findByUserId(id);
            if(employee==null) throw new ApiException(
                    "Błąd przy pobieraniu projektów",HttpStatus.BAD_REQUEST,"Nie znaleziono pracownika");

            if (employee.getProjects() != null) {
                employee.getProjects().stream().forEach(p -> projectsDTO.add(new ProjectDTO(p)));
            }}

        return projectsDTO;
    }



    public ProjectDTO getProject(int id) throws ApiException {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ApiException("Błąd przy pobieraniu projektu",
                        HttpStatus.BAD_REQUEST, "Nie znaleziono projektu"
        ));
        return new ProjectDTO(project);
    }


}
