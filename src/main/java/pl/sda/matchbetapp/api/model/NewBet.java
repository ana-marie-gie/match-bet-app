package pl.sda.matchbetapp.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Service
public class NewBet {

    private long id;
    @NotNull(message = "Match id is missing")
    private Long matchId;
    @NotNull(message = "User id is missing")
    private Long userId;
    private Integer firstTeamResult;
    private Integer secondTeamResult;

    @AssertTrue(message = "Result needs to be correct")
    public boolean isCorrectResult(){
        return firstTeamResult >= 0 && firstTeamResult <= 100
        && secondTeamResult >= 0 && secondTeamResult < 100;
    }
}
