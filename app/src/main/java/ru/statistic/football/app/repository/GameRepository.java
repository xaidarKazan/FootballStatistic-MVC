package ru.statistic.football.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.statistic.football.app.Entity.Game;
import ru.statistic.football.app.Entity.Season;
import ru.statistic.football.app.Entity.Team;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, PagingAndSortingRepository<Game, Long> {
    boolean existsByTourAndSeasonAndTeam1AndTeam2AndScore(Integer tour, Season season,
                                                           Team team1, Team team2, String score);
    List<Game> getGamesByTeam1AndTeam2(Team team1, Team team2);

    @Query("SELECT g FROM Game g WHERE g.season=:lastSeason AND (g.team1=:team OR g.team2=:team)")
    List<Game> findAllGamesSeason(@Param("lastSeason") Season season, @Param("team") Team team);

    @Query(value = "SELECT g FROM Game g WHERE g.season=:needed_season AND (g.team1=:team OR g.team2=:team) ORDER BY g.id_game DESC" )
    Page<Game> find(@Param("needed_season") Season season, @Param("team") Team team, Pageable pageable);

}
