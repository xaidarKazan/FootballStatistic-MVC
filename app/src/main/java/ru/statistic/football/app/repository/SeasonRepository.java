package ru.statistic.football.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.statistic.football.app.Entity.Season;

import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    Season getByValue(String value);
    boolean existsByValue(String value);

    @Query("SELECT s FROM Season s WHERE s.id_season=(select max(s1.id_season) from Season s1)")
    Season getLastSeason();
}
