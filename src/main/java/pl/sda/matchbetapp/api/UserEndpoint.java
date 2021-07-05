package pl.sda.matchbetapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.matchbetapp.api.model.User;
import pl.sda.matchbetapp.service.UserService;

import javax.validation.Valid;
import java.util.List;

/*
Bazując na API matchy stwórz endpoint, który w oddzielnym pliku zapisze (operacje CRUD)
 użytkowników naszego systemu. W pliku zapiszemy id, login, firstName, lastName
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserEndpoint {

        private final UserService userService;


        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public void createNewUser(@Valid @RequestBody User user) {
            userService.createUser(user);
        }

        @PutMapping
        public void updateUser(@Valid @RequestBody User user) {
            userService.updateUser(user);
        }

        @DeleteMapping
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteUser(@RequestParam Long id) {
            userService.deleteUser(id);
        }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }
}
