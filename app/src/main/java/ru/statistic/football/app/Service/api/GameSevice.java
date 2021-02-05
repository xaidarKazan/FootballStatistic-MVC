package ru.statistic.football.app.Service.api;

import ru.statistic.football.app.Entity.Game;
import ru.statistic.football.app.Entity.Season;
import ru.statistic.football.app.Entity.Team;

import java.util.List;

public interface GameSevice {
    Game getGameById(Long id);
    List<Game> getAllGamesInSeason(Season season, Team team);
    List<Game> getGamesWithOwnersAndGuests(Team owners, Team guests);
    List<Game> getLast6Matches(Season season, Team team);
    void saveGame(Game game);
}
