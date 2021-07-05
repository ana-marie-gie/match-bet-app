package pl.sda.matchbetapp.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name="bets")
public class BetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer firstTeamResult;
    private Integer secondTeamResult;

    @ManyToOne
    private MatchEntity match;

    @ManyToOne
    private UserEntity user;
}
