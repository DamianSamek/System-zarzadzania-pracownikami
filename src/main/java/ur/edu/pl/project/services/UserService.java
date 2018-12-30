package ur.edu.pl.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.model.Employee;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.model.dto.EmployeeDTO;
import ur.edu.pl.project.repositories.EmployeeRepository;
import ur.edu.pl.project.repositories.RoleRepository;
import ur.edu.pl.project.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final EmployeeRepository employeeRepository;
	private final ObjectMapper objectMapper;
	private final EncryptionService encryptionService;
	private final RoleRepository roleRepository;

	private static final String USER_NOT_EXIST = "Użytkownik o podanym emailu nie istnieje.";

	@Autowired
	public UserService(UserRepository uRepo, RoleRepository rRepo, ObjectMapper objectMapper,
			EncryptionService encryptionService, EmployeeRepository er) {
		this.objectMapper = objectMapper;
		this.userRepository = uRepo;
		this.roleRepository = rRepo;
		this.encryptionService = encryptionService;
		this.employeeRepository = er;
	}

	public String toJson(String userEmail) throws JsonProcessingException {
		User user = userRepository.findByEmail(userEmail);
		String result = "";
		if (user != null) {
			result = objectMapper.writeValueAsString(user);
		}
		return result;
	}

	public EmployeeDTO getEmployeeData(int id) {
		User user = userRepository.findById(id).get();

		Employee employee=employeeRepository.findByUserId(user.getId());

		return new EmployeeDTO(user.getFirstName(),
				user.getSecondName(),user.getEmail(),employee.getPhone(),
				employee.getPosition(),employee.getStreetAddress(),
				employee.getCity(),employee.getPostalCode(),
				employee.getState(),"","");
	}

}
