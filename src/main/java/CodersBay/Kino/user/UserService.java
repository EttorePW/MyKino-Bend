package CodersBay.Kino.user;

import CodersBay.Kino.controllerExceptionhandler.customExeption.NotFoundException;
import CodersBay.Kino.controllerExceptionhandler.customExeption.PasswordNotMatch;
import CodersBay.Kino.user.dtos.NewUserDTO;
import CodersBay.Kino.user.dtos.RespondUserDTO;
import CodersBay.Kino.user.dtos.request.ControllUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepoitory userRepoitory;

    public List<RespondUserDTO> getAllUsers() {
        List<User> users = userRepoitory.findAll();
        List<RespondUserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> {
            userDTOs.add(convertUserToUserDTO(user));
        });
        return userDTOs;
    }

    public RespondUserDTO convertUserToUserDTO(User user) {
        return RespondUserDTO.builder()
                .userId(user.getUserId())
                .userFirstName(user.getUserFirstName())
                .userLastName(user.getUserLastName())
                .userEmail(user.getUserEmail())
                .build();
    }
    public RespondUserDTO createNewUser(NewUserDTO newUserDTO) {
        User user = User.builder()
                .userFirstName(newUserDTO.getUserFirstName())
                .userLastName(newUserDTO.getUserLastName())
                .userEmail(newUserDTO.getUserEmail())
                .userPassword(newUserDTO.getUserPassword())
                .confirmPassword(newUserDTO.getUserPassword())
                .build();
        userRepoitory.save(user);

        return convertUserToUserDTO(user);
    }

    public String deleteUserById(Long id) {
        userRepoitory.deleteById(id);
        return "User with id: " + id + " was deleted";
    }

    public RespondUserDTO updateTheUser(Long id, NewUserDTO newUserDTO) {
        User user = userRepoitory.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " was not found", "/api/users/"+id));
        user.setUserFirstName(newUserDTO.getUserFirstName());
        user.setUserLastName(newUserDTO.getUserLastName());
        user.setUserEmail(newUserDTO.getUserEmail());
        user.setUserPassword(newUserDTO.getUserPassword());
        userRepoitory.save(user);

        return convertUserToUserDTO(user);
    }

    public Object getControlledUser(String email, ControllUser controllUser) {
        User user = userRepoitory.findByUserEmail(email);
        System.out.println(user.getUserPassword());
        System.out.println(controllUser.getPassword());
        if (user == null) {
            throw new NotFoundException("User with email: " + email + " was not found", "/api/users/"+email);
        }
        if (user.getUserPassword().equalsIgnoreCase(controllUser.getPassword())) {
            return convertUserToUserDTO(user);
        }
         throw new PasswordNotMatch("Please check your password, because you it is not the correct password");
    }
}
