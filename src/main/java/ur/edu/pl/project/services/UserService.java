package ur.edu.pl.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ur.edu.pl.project.exceptions.ErrorResponseCodes;
import ur.edu.pl.project.exceptions.UserCreateException;
import ur.edu.pl.project.model.User;
import ur.edu.pl.project.repositories.RoleRepository;
import ur.edu.pl.project.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository uRepo, RoleRepository rRepo) {

        this.userRepository = uRepo;
        this.roleRepository = rRepo;

    }


    public User createUser(User user) throws UserCreateException  {

        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser == null) {
            user.setRole(roleRepository.findByRole(user.getRole().toString()));
            String pass = user.getPassword();
            //user.setPassword(encryptionService.encode(user.getPassword()));
            user.setPassword(pass);
            user.setEnabled(true);
            userRepository.save(user);

        } else {
            throw new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR, HttpStatus.BAD_REQUEST,
                    "Uzytkownik o podanym mailu istnieje");
        }
        return user;
    }

//    public User modifyUser(User user) throws UserCreateException {
//
//        User existingUser = userRepository.findById(user.getId())
//                .orElseThrow(() -> new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR,
//                        HttpStatus.BAD_REQUEST, String.format("Użytownik %s nie istnieje.", user.getEmail())));
//
//        if (user.getFirstName() != null)
//            existingUser.setFirstName(user.getFirstName());
//        if (user.getSecondName() != null)
//            existingUser.setSecondName(user.getSecondName());
//        userRepository.save(existingUser);
//        return existingUser;
//    }
//
//    public User modifyUserForAdmin(User user) throws UserCreateException {
//
//        User existingUser = userRepository.findById(user.getId())
//                .orElseThrow(() -> new UserCreateException(ErrorResponseCodes.User.USER_CREATE_ERROR,
//                        HttpStatus.BAD_REQUEST, String.format("Użytownik %s nie istnieje.", user.getEmail())));
//
//        if (user.getFirstName() != null)
//            existingUser.setFirstName(user.getFirstName());
//
//        if (user.getSecondName() != null)
//            existingUser.setSecondName(user.getSecondName());
//
//        existingUser.setEnabled(user.isEnabled());
//
//        userRepository.save(existingUser);
//        return existingUser;
//    }



}
