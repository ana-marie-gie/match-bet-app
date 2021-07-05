package pl.sda.matchbetapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.matchbetapp.exception.MatchNotFoundException;
import pl.sda.matchbetapp.api.model.BetDetails;
import pl.sda.matchbetapp.api.model.NewBet;
import pl.sda.matchbetapp.repository.BetEntity;
import pl.sda.matchbetapp.repository.BetRepository;
import pl.sda.matchbetapp.repository.MatchEntity;
import pl.sda.matchbetapp.repository.UserEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BetService {
    private final BetRepository betRepository;
    private final MatchService matchService;

    public void createBet(NewBet bet) {
        validateBet(bet);
        BetEntity entity = BetEntity.builder()
                .firstTeamResult(bet.getFirstTeamResult())
                .secondTeamResult(bet.getSecondTeamResult())
                .match(MatchEntity.builder().id(bet.getMatchId()).build())
                .user(UserEntity.builder().id(bet.getUserId()).build())
                .build();

        betRepository.save(entity);
    }

    public void deleteBet(Long id){
        betRepository.deleteById(id);
    }

    public List<BetDetails> getAllForUser(Long userId) {
        return null;
    }

    public List<BetDetails> getAllForMatch(Long matchId) {
        return null;
    }




    private void validateBet(NewBet bet) {
        if (!matchService.checkIfMatchExists(bet.getMatchId())) {
            throw new MatchNotFoundException("Match not found");
        }
        if(bet.getFirstTeamResult() < 0|| bet.getSecondTeamResult() < 0){
            throw new IllegalArgumentException("Result needs to be positive number");
        }
    }
}
