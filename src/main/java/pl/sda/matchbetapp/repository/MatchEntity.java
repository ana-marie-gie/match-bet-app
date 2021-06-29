package pl.sda.matchbetapp.repository;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MatchEntity {
    @Setter
    private Long id;
    private String firstTeam;
    private String secondTeam;
    private LocalDateTime startTime;
}