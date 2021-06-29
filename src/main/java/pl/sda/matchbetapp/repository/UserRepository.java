package pl.sda.matchbetapp.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Repository
public class UserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private String usersFilePath;

    public UserRepository(@Value("${file.path.user}") String usersFilePath) {
        this.usersFilePath = usersFilePath;
    }

    public void save(UserEntity user) {
        List<UserEntity> allUsers = readFromFile();
        if (user.getId() == null) {
            long currentMaxId = allUsers.stream().mapToLong(us -> us.getId()).max().orElse(0L);
            user.setId(++currentMaxId);
        } else {
            allUsers.removeIf(userEntity -> userEntity.getId().equals(user.getId()));
        }
        allUsers.add(user);
        storeInFile(allUsers);
    }

    public List<UserEntity> getAll() {
        return readFromFile();
    }

    public void delete(Long id) {
        List<UserEntity> allUsers = readFromFile();
        allUsers.removeIf(us -> us.getId().equals(id));
        storeInFile(allUsers);
    }

    //ZOSTAWIAMY WIELOWĄTKOWOSC - NA TYM ETAPIE ZAKLADAMY ZE BEDZIEMY PRZETWARZAC 1 REQUEST NA RAZ
    private void storeInFile(List<UserEntity> userEntities) {
        Path path = Paths.get(usersFilePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (UserEntity user : userEntities) {
                writer.write(String.format("%d;%s;%s;%s", user.getId(),
                        user.getFirstName(), user.getLastName(), user.getEmail()));
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("Error in storing users", e);
        }
    }

    private List<UserEntity> readFromFile() {
        Path path = Paths.get(usersFilePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines()
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split(";"))
                    .map(fields -> UserEntity.builder()
                            .id(Long.valueOf(fields[0]))
                            .firstName(fields[1])
                            .lastName(fields[2])
                            .email(fields[3])
                            .build())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error in reading users", e);
        }

        return new ArrayList<>();
    }
}
