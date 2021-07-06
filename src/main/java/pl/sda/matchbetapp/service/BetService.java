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
import java.util.stream.Collectors;

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
        return betRepository.findAllByUser_id(userId)
                .stream()
                .map(this::toDetails)
                .collect(Collectors.toList());
    }



    public List<BetDetails> getAllForMatch(Long matchId) {
        return betRepository.findAllByMatch_id(matchId)
                .stream()
                .map(this::toDetails)
                .collect(Collectors.toList());
    }

    private BetDetails toDetails(BetEntity ent) {
        UserEntity user = ent.getUser();
        MatchEntity match = ent.getMatch();
        return BetDetails.builder()
                .id(ent.getId())
                .firstTeam(match.getFirstTeam())
                .secondTeam(match.getSecondTeam())
                .startTime(match.getStartTime())
                .userLogin(user.getLogin())
                .userName(user.getFirstName() + " " + user.getLastName())
                .firstTeamResult(ent.getFirstTeamResult())
                .secondTeamResult(ent.getSecondTeamResult())
                .build();
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
