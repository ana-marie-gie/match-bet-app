package pl.sda.matchbetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<BetEntity, Long> {

    List<BetEntity> findAllByUser_id(Long userId);

    List<BetEntity> findAllByMatch_id(Long matchId);


}
