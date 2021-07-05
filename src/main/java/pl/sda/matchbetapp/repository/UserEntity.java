package pl.sda.matchbetapp.repository;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user")
    private Set<BetEntity> bets;
}
