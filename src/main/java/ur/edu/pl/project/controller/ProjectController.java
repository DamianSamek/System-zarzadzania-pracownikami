package ur.edu.pl.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ur.edu.pl.project.model.dto.ProjectDTO;
import ur.edu.pl.project.services.AuthService;
import ur.edu.pl.project.services.ProjectService;

import java.security.Principal;

@RepositoryRestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthService authService;


    @RequestMapping(method= RequestMethod.POST, value="/project/")
    public ResponseEntity<?> createProject(@RequestBody  ProjectDTO projectDTO) {
        projectService.createProject(projectDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method= RequestMethod.PATCH, value="/project/{id}")
    public ResponseEntity<?> updateProject(@PathVariable("id") int id, @RequestBody ProjectDTO projectDTO)
    {
        projectService.updateProject(id, projectDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/project/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") int id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/project/{id}")
    public ResponseEntity<?> getProjects(@PathVariable("id") int id){
        System.out.println("INSIDE CONTROLLER");

        if(authService.currentUser().getRole().getRole().equals("ROLE_EMPLOYEE"))
        return new ResponseEntity<>(projectService.getProjects(id),
                HttpStatus.OK);
        else if(authService.currentUser().getRole().getRole().equals("ROLE_MANAGER"))
            return new ResponseEntity<>(projectService.getProject(id),HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
