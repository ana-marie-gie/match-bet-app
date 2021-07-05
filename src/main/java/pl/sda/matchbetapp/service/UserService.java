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

    private final UserRepository repository;

    public void create(User user) {
        repository.save(UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .build());
    }

    public void update(User user) {
        repository.save(UserEntity.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .build());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<User> getAll() {
        return repository.findAll().stream()
                .map(ent -> User.builder()
                        .id(ent.getId())
                        .firstName(ent.getFirstName())
                        .lastName(ent.getLastName())
                        .login(ent.getLogin())
                        .build())
                .collect(Collectors.toList());
    }

}
