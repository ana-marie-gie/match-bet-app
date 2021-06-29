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
public class MatchRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchRepository.class);

    private String matchesFilePath;

    public MatchRepository(@Value("${file.path.match}") String matchesFilePath) {
        this.matchesFilePath = matchesFilePath;
    }

    public void save(MatchEntity match) {
        List<MatchEntity> allMatches = readFromFile();
        if (match.getId() == null) {
            long currentMaxId = allMatches.stream().mapToLong(mat -> mat.getId()).max().orElse(0L);
            match.setId(++currentMaxId);
        } else {
            allMatches.removeIf(ent -> ent.getId().equals(match.getId()));
        }
        allMatches.add(match);
        storeInFile(allMatches);
    }

    public List<MatchEntity> getAll() {
        return readFromFile();
    }

    public void delete(Long id) {
        List<MatchEntity> allMatches = readFromFile();
        allMatches.removeIf(mat -> mat.getId().equals(id));
        storeInFile(allMatches);
    }

    //ZOSTAWIAMY WIELOWĄTKOWOSC - NA TYM ETAPIE ZAKLADAMY ZE BEDZIEMY PRZETWARZAC 1 REQUEST NA RAZ
    private void storeInFile(List<MatchEntity> matchEntities) {
        Path path = Paths.get(matchesFilePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (MatchEntity match : matchEntities) {
                writer.write(String.format("%d;%s;%s;%s", match.getId(),
                        match.getFirstTeam(), match.getSecondTeam(), match.getStartTime().toString()));
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.error("Error in storing matches", e);
        }
    }

    private List<MatchEntity> readFromFile() {
        Path path = Paths.get(matchesFilePath);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.lines()
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split(";"))
                    .map(fields -> MatchEntity.builder()
                            .id(Long.valueOf(fields[0]))
                            .firstTeam(fields[1])
                            .secondTeam(fields[2])
                            .startTime(LocalDateTime.parse(fields[3]))
                            .build())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Error in reading matches", e);
        }

        return new ArrayList<>();
    }

}
