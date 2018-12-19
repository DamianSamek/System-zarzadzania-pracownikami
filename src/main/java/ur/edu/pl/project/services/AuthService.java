package ur.edu.pl.project.services;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository; 
	}

    public User currentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return null;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return null;

		Object user = authentication.getPrincipal();
		if (user == null)
			return null;

		if (user.getClass().equals(String.class)) {
			String userEmail = (String) user;
			return userRepository.findByEmail(userEmail);
		} else if (user.getClass().equals(org.springframework.security.core.userdetails.User.class)) {
			org.springframework.security.core.userdetails.User cUser = (org.springframework.security.core.userdetails.User) user;
			User nUser = new User();
			nUser.setEmail(cUser.getUsername());
			return nUser;
		}

		return null;

    }
}