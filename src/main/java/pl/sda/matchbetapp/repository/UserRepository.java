package pl.sda.matchbetapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity,Long> {

    List<UserEntity> findByLogin(String username);
}
