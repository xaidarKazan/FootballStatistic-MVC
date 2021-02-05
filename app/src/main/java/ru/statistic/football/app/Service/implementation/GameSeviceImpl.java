package ru.statistic.football.app.Service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.statistic.football.app.Entity.Game;
import ru.statistic.football.app.Entity.Season;
import ru.statistic.football.app.Entity.Team;
import ru.statistic.football.app.Service.api.GameSevice;
import ru.statistic.football.app.repository.GameRepository;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GameSeviceImpl implements GameSevice {

    private final GameRepository gameRepository;

    @Override
    public Game getGameById(Long id) {
        return gameRepository.getOne(id);
    }

    @Override
    public List<Game> getAllGamesInSeason(Season season, Team team) {
        return gameRepository.findAllGamesSeason(season, team);
    }

    @Override
    public List<Game> getGamesWithOwnersAndGuests(Team owners, Team guests) {
        return gameRepository.getGamesByTeam1AndTeam2(owners, guests);
    }

    @Override
    public List<Game> getLast6Matches(Season season, Team team) {
        Pageable pageable = PageRequest.of(0, 6);
        return gameRepository.find(season, team, pageable).getContent();
    }

    @Override
    public void saveGame(Game game) {
        if (!gameRepository.existsByTourAndSeasonAndTeam1AndTeam2AndScore(game.getTour(),
                                                                          game.getSeason(),
                                                                          game.getTeam1(),
                                                                          game.getTeam2(),
                                                                          game.getScore())) {
            gameRepository.save(game);
        }
    }
}
