package ur.edu.pl.project.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ur.edu.pl.project.model.Project;
import ur.edu.pl.project.model.Role;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Role role;


    @Before
    public void setUp() throws Exception {
        role = new Role();
        role.setRole("ROLE1");
        roleRepository.save(role);

    }

    @After
    public void after() throws Exception {
        //roleRepository.deleteAll();
        roleRepository.delete(roleRepository.findByRole("ROLE1"));
        roleRepository.delete(roleRepository.findByRole("ROLE2"));
    }

//    @Test
//    public void deleteAll_Test() {
//        roleRepository.deleteAll();
//        assertTrue(roleRepository.findAll().isEmpty());
//    }

    @Test
    public void getRoleByRole_Test() {
        boolean add;
        if (roleRepository.findByRole("ROLE1")!=null){
            add = true;
        } else add = false;
        assertTrue(add);
    }

    @Test
    public void saveRole_Test() {
        Role role2 = new Role();
        role2.setRole("ROLE2");
        boolean add;
        if (roleRepository.save(role2) != null) {
            add = true;
        } else add = false;
        assertTrue(add);
    }

    @Test
    public void findAllRoles_Test() {
        List<Role> roles = roleRepository.findAll();
        assertNotNull(roles);
        assertTrue(roles.size() > 0);
    }
}