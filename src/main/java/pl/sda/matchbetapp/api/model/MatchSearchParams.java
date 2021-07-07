package pl.sda.matchbetapp.api.model;

import lombok.*;

import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MatchSearchParams {
    private String firstTeam;
    private String secondTeam;
    private boolean searchAnyCombination;
    @Future(message = "Date of match cannot be from the past")
    private LocalDateTime startTimeFrom;
    @Future(message = "Date of match cannot be from the past")
    private LocalDateTime StartTimeTo;
}
