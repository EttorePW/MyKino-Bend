package CodersBay.Kino.user;


import CodersBay.Kino.user.dtos.NewUserDTO;
import CodersBay.Kino.user.dtos.request.ControllUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    @PostMapping("/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email,@RequestBody ControllUser user) {
       return new ResponseEntity<>(userService.getControlledUser(email,user),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody NewUserDTO user) {
        return new ResponseEntity<>(userService.createNewUser(user),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody NewUserDTO newUserDTO) {
       return new ResponseEntity<>(userService.updateTheUser(id, newUserDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return new ResponseEntity<>(userService.deleteUserById(id),HttpStatus.OK);
    }
}
