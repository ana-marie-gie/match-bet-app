package pl.sda.matchbetapp.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sda.matchbetapp.api.model.User;
import pl.sda.matchbetapp.service.UserService;

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

        @GetMapping
        public List<User> getAll() {
            return userService.getAll();
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public void createUser(@RequestBody User user) {
            userService.create(user);
        }

        @PutMapping
        public void updateUser(@RequestBody User user) {
            userService.update(user);
        }

        @DeleteMapping
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteMatch(@RequestParam Long id) {
            userService.delete(id);
        }
}
