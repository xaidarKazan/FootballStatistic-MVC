package ru.statistic.football.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.statistic.football.app.Entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team getByName(String name);
    boolean existsByName(String name);
}
