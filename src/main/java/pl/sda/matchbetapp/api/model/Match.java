package pl.sda.matchbetapp.api.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {
    private Long id;
    private String firstTeam;
    private String secondTeam;
    private LocalDateTime startTime;

}
