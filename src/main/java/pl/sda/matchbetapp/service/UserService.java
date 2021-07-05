package pl.sda.matchbetapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.matchbetapp.api.model.User;
import pl.sda.matchbetapp.repository.UserEntity;
import pl.sda.matchbetapp.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .build());
    }

    public void updateUser(User user) {
        userRepository.save(UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .id(user.getId())
                .build());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll().stream()
                .map(ent -> User.builder()
                        .id(ent.getId())
                        .firstName(ent.getFirstName())
                        .lastName(ent.getLastName())
                        .login(ent.getLogin())
                        .build())
                .collect(Collectors.toList());
    }

}
