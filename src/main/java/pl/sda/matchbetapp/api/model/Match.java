package pl.sda.matchbetapp.api.model;

import lombok.*;
import pl.sda.matchbetapp.api.validator.DifferentTeams;
import pl.sda.matchbetapp.api.validator.TeamName;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DifferentTeams
public class Match {
    private Long id;
    @TeamName
    @NotBlank(message = "First name team cannot be blank")
    private String firstTeam;
    @TeamName
    @NotBlank(message = "Second name team cannot be blank")
    private String secondTeam;
    @Future(message = "Date of match cannot be from the past")
    private LocalDateTime startTime;

    @AssertTrue(message = "Matches start after noon")
    public boolean isStartTimeAfternoon(){
        return startTime.getHour() >= 12;
    }
}
