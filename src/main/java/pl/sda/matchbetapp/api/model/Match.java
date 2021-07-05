package pl.sda.matchbetapp.api.model;

import lombok.*;
import pl.sda.matchbetapp.api.validator.DifferentTeams;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DifferentTeams
public class Match {
    private Long id;
    private String firstTeam;
    private String secondTeam;
    private LocalDateTime startTime;

}
