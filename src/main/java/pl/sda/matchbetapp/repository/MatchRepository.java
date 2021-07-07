package pl.sda.matchbetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<MatchEntity,Long>, CustomMatchRepository{
    List<MatchEntity> findAllByStartTimeIsBetween(LocalDateTime from, LocalDateTime to);

    List<MatchEntity> findAllByFirstTeamOrSecondTeam(String team1, String team2);

    List<MatchEntity> findAllByFirstTeamIn(List<String> possibleTeams);
}
