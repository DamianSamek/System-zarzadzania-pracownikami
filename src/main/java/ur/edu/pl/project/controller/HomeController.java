package ur.edu.pl.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.repositories.UserRepository;

import java.util.List;

@RestController
public class HomeController {


    @RequestMapping("/")
    public String hello() {

        return "hello world!";
    }
}
