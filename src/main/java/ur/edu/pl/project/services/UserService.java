package ur.edu.pl.project.services;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.APIAuthenticationException;
import ur.edu.pl.project.exceptions.ErrorResponseCodes;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.model.dto.ChangePasswordDTO;
import ur.edu.pl.project.repositories.RoleRepository;
import ur.edu.pl.project.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	private final EncryptionService encryptionService;
	private final RoleRepository roleRepository;

	private static final String USER_NOT_EXIST = "Użytkownik o podanym emailu nie istnieje.";

	@Autowired
	public UserService(UserRepository uRepo, RoleRepository rRepo, ObjectMapper objectMapper,
			EncryptionService encryptionService) {
		this.objectMapper = objectMapper;
		this.userRepository = uRepo;
		this.roleRepository = rRepo;
		this.encryptionService = encryptionService;
	}

	public User createUser(User user) throws UserCreateException {

		User existingUser = userRepository.findByEmail(user.getEmail());

		if (existingUser == null) {
			user.setRole(roleRepository.findByRole(user.getRole().getRole().toString()));
			String pass = user.getPassword();
			user.setPassword(encryptionService.encode(user.getPassword()));
			user.setEnabled(true);
			user.setTokenValid(true);
			userRepository.save(user);

		} else {
			throw new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR, HttpStatus.BAD_REQUEST,
					"Uzytkownik o podanym mailu istnieje");
		}
		return user;
	}


	public User modifyUser(User user) throws UserCreateException {

		User existingUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR,
						HttpStatus.BAD_REQUEST, String.format("Użytownik %s nie istnieje.", user.getEmail())));

		if (user.getFirstName() != null)
			existingUser.setFirstName(user.getFirstName());
		if (user.getSecondName() != null)
			existingUser.setSecondName(user.getSecondName());
		userRepository.save(existingUser);
		return existingUser;
	}

	public User modifyUserForAdmin(User user) throws UserCreateException {

		User existingUser = userRepository.findById(user.getId())
				.orElseThrow(() -> new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR,
						HttpStatus.BAD_REQUEST, String.format("Użytownik %s nie istnieje.", user.getEmail())));

		if (user.getFirstName() != null)
			existingUser.setFirstName(user.getFirstName());

		if (user.getSecondName() != null)
			existingUser.setSecondName(user.getSecondName());

		existingUser.setEnabled(user.isEnabled());

		userRepository.save(existingUser);
		return existingUser;
	}

	public User adminChangePassword(ChangePasswordDTO changePassword)
			throws APIAuthenticationException, UserCreateException {
		boolean tokenIsValid = validateToken(changePassword.getToken());

		if (!tokenIsValid) {
			throw new APIAuthenticationException(ErrorResponseCodes.Authentication.TOKEN_EXPIRED,
					HttpStatus.UNAUTHORIZED, "Token do zmiany hasła wygasł.");
		}
		if (!changePassword.getPassword().equals(changePassword.getConfirmPassword())) {
			throw new APIAuthenticationException(ErrorResponseCodes.Authentication.PASSWORDS_NOT_EQUAL,
					HttpStatus.BAD_REQUEST, "Hasła muszą być identyczne.");
		}

		User user = userRepository.findByResetToken(changePassword.getToken());
		user.setPassword(encryptionService.encode(changePassword.getPassword()));
			invalidateToken(user);
			return userRepository.save(user);
	}


	private String generatePassword() {

		return "";//encryptionService.encode(RandomStringUtils.randomAlphanumeric(8));
	}

	public void createResetPasswordToken(User user) throws UserCreateException {

		User existingUser = userRepository.findByEmail(user.getEmail());

		if (existingUser != null) {
			String token = UUID.randomUUID().toString();
			existingUser.setResetToken(token);
			existingUser.setValidDate(LocalDate.now().plusDays(1));
			existingUser.setTokenValid(true);
			userRepository.save(existingUser);


		} else {
			throw new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR, HttpStatus.BAD_REQUEST,
					USER_NOT_EXIST);
		}
	}

	public boolean validateToken(String token) {

		User existingUser = userRepository.findByResetToken(token);

		if (existingUser != null) {
			if (LocalDate.now().isAfter(existingUser.getValidDate()))
				existingUser.setTokenValid(false);
			return existingUser.isTokenValid();
		}
		return false;
	}

	public User changePassword(ChangePasswordDTO changePassword) throws APIAuthenticationException {
		boolean tokenIsValid = validateToken(changePassword.getToken());

		if (!tokenIsValid) {
			throw new APIAuthenticationException(ErrorResponseCodes.Authentication.TOKEN_EXPIRED,
					HttpStatus.UNAUTHORIZED, "Token do zmiany hasła wygasł.");
		}
		if (!changePassword.getPassword().equals(changePassword.getConfirmPassword())) {
			throw new APIAuthenticationException(ErrorResponseCodes.Authentication.PASSWORDS_NOT_EQUAL,
					HttpStatus.BAD_REQUEST, "Hasła muszą być identyczne.");
		}

		User user = userRepository.findByResetToken(changePassword.getToken());
		user.setPassword(encryptionService.encode(changePassword.getPassword()));
		invalidateToken(user);
		return userRepository.save(user);

	}

	private void invalidateToken(User user) {
		user.setTokenValid(false);
		user.setResetToken(null);
	}

	public String toJson(String userEmail) throws JsonProcessingException {
		User user = userRepository.findByEmail(userEmail);
		String result = "";
		if (user != null) {
			result = objectMapper.writeValueAsString(user);
		}
		return result;
	}

}
