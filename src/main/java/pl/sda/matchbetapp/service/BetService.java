package pl.sda.matchbetapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.matchbetapp.api.model.Bet;
import pl.sda.matchbetapp.exception.MatchNotFoundException;
import pl.sda.matchbetapp.repository.MatchEntity;

@Service
@RequiredArgsConstructor
public class BetService {
    private final MatchService matchService;

    public void createBet(Bet bet) {
        if (!matchService.checkIfMatchExists(bet.getMatchId())) {
            throw new MatchNotFoundException("Match not found");
        }
        if (bet.getFirstTeamResult() < 0 || bet.getSecondTeamResult() < 0) {
            throw new IllegalStateException("Result have to be positive.");
        }
            //bet entity and repository to create + bets file

    }
}
