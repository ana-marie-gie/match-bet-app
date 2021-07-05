package pl.sda.matchbetapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.matchbetapp.api.model.Match;
import pl.sda.matchbetapp.exception.DateInPastException;
import pl.sda.matchbetapp.exception.MatchNotFoundException;
import pl.sda.matchbetapp.repository.MatchEntity;
import pl.sda.matchbetapp.repository.MatchRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public boolean checkIfMatchExists(Long id){
        return matchRepository.existsById(id);
    }
    public void createMatch(Match match) {
        validateStartTimeAndIfIsEmpty(match);

        matchRepository.save(MatchEntity.builder()
                .firstTeam(match.getFirstTeam())
                .secondTeam(match.getSecondTeam())
                .startTime(match.getStartTime())
                .build());
    }

    public void update(Match match) {
        validateMatchId(match.getId());
        validateStartTimeAndIfIsEmpty(match);

        matchRepository.save(MatchEntity.builder()
                .id(match.getId())
                .firstTeam(match.getFirstTeam())
                .secondTeam(match.getSecondTeam())
                .startTime(match.getStartTime())
                .build());
    }

    public void delete(Long id) {
        validateMatchId(id);

        matchRepository.deleteById(id);
    }

    public List<Match> getAll() {
        return matchRepository.findAll().stream()
                .map(ent -> Match.builder()
                        .id(ent.getId())
                        .firstTeam(ent.getFirstTeam())
                        .secondTeam(ent.getSecondTeam())
                        .startTime(ent.getStartTime())
                        .build())
                .collect(Collectors.toList());
    }

    private void validateMatchId(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new MatchNotFoundException("Match not found");
        }
    }

    private void validateStartTimeAndIfIsEmpty(Match match) {
        if (LocalDateTime.now().isAfter(match.getStartTime())) {
            throw new DateInPastException("Time is from the past");
        }
        if (match.getFirstTeam().isEmpty() ||
                match.getSecondTeam().isEmpty()) {
            throw new IllegalStateException("You didn't add team name");
        }
    }



}
