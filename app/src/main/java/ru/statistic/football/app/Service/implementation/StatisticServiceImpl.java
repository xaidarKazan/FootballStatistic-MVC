package ru.statistic.football.app.Service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.statistic.football.app.Entity.Game;
import ru.statistic.football.app.Entity.Season;
import ru.statistic.football.app.Entity.Team;
import ru.statistic.football.app.Service.api.GameSevice;
import ru.statistic.football.app.Service.api.SeasonSevice;
import ru.statistic.football.app.Service.api.StatisticService;
import ru.statistic.football.app.Service.api.TeamService;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final SeasonSevice seasonSevice;
    private final TeamService teamService;
    private final GameSevice gameSevice;

    private Team getTeam(String teamName) {
        return teamService.getTeam(teamName);
    }

    //Статистика Побед хозяив ничьи Победа гостей
    @Override
    public List<Integer> getStatWinOwnersDrawWinGuests(String team1, String team2) {
        Team owners = getTeam(team1);
        Team guests = getTeam(team2);
        List<Game> gameList = gameSevice.getGamesWithOwnersAndGuests(owners, guests);
        Integer gamesAmount = gameList.size();
        Integer ownersWinCount = 0;
        Integer guestsWinCount = 0;
        for (Game game: gameList) {
            try {
                String[] score = game.getScore().split(":");
                int ownersGoals = Integer.parseInt(score[0].trim());
                int guestsGoals = Integer.parseInt(score[1].trim());
                if (ownersGoals > guestsGoals) ownersWinCount++;
                if(ownersGoals < guestsGoals) guestsWinCount++;
            }
            catch (NumberFormatException e) {
            }
        }
        List<Integer> result = new ArrayList<>();
        result.add(gamesAmount ==0 ? 0 : ownersWinCount*100/gamesAmount);  // победа хозяев в процентах
        result.add(gamesAmount ==0 ? 0 : guestsWinCount*100/gamesAmount); // победа гостей
        result.add(100 - (result.get(0) + result.get(1)));               // ничьи
        return result;
    }

    // Статич=стика последних 6 игр
    @Override
    public List<String> getStatisticsOfTheLast6Matches(String teamName) {
        Season season = seasonSevice.getLastSeason();
        Team team = getTeam(teamName);
        List<String> result = new ArrayList<>();
        List<Game> gameListOwners = gameSevice.getLast6Matches(season, team);
        for (Game game : gameListOwners) {
            try {
                String[] score = game.getScore().split(":");
                int ownersGoals = Integer.parseInt(score[0].trim());
                int guestsGoals = Integer.parseInt(score[1].trim());
                //если счет равный записываем ничью т.е. 0
                if (ownersGoals == guestsGoals) result.add("Н");
                else {
                    // если выйграла ком1 и ком1 это owners тогда записываем победу т.е. 1
                    if (ownersGoals > guestsGoals && game.getTeam1() == team) result.add("В");
                    else {
                        // если выйграла ком2 и ком2 это owners тогда записываем победу т.е. 1
                        if (ownersGoals < guestsGoals && game.getTeam2() == team) result.add("В");
                            // в остальных случаях поражение т.е. -1
                        else result.add("П");
                    }
                }
            }
            catch (NumberFormatException e) {
                result.add("-");
            }
        }
        return result;
    }

    @Override
    public List<String> getFullSeasonStatistics(String team1, String team2) {
        Season lastSeason = seasonSevice.getLastSeason();
        Team owners = getTeam(team1);
        Team guests = getTeam(team2);
        List<String> result = new ArrayList<>();
        List<Game> fullStatOwners = gameSevice.getAllGamesInSeason(lastSeason, owners);
        List<Game> fullStatGuests = gameSevice.getAllGamesInSeason(lastSeason, guests);

        /*  Запись в массив результатов
                       owners    guests
        *   Матчи         1         2
        *   Победы        4         5
        *   Ничьи         7         8
        *   Поражения     10        11
        *   Забито        13        14
        *   Пропущено     16        17

         */

        Integer winCount = 0,
            drawCount = 0,
            loseCount = 0,
            matchsCount = 0,
            goalsCount = 0,
            concededCount = 0;
        for (Game game: fullStatOwners) {
            int matchResult = getWinDrawLoss(game, owners);
            if (matchResult == 1) winCount++;     // запись победы
            if (matchResult == 0) drawCount++;   // запись нечьи
            if (matchResult == -1) loseCount++; // запись поражения
            String scoreString = game.getScore();
            if (!scoreString.contains("-")) {
                matchsCount++; // Количество матчей
                String[] score = game.getScore().split(":");
                int ownersGoals = Integer.parseInt(score[0].trim());
                int guestsGoals = Integer.parseInt(score[1].trim());
                if (game.getTeam1() == owners) {                             // если играли дома
                    goalsCount = goalsCount + ownersGoals;                  // запись забитых голов
                    concededCount = concededCount + guestsGoals;           // запись пропущенных голов
                }
                else {                                                       // если играли в гостях
                    goalsCount = goalsCount + guestsGoals;                  // запись забитых голов
                    concededCount = concededCount + ownersGoals;           // запись пропущенных голов
                }
            }
        }

        Integer guestsWinCount = 0,
            guestsDrawCount = 0,
            guestsLoseCount = 0,
            guestsMatchsCount = 0,
            guestsGoalsCount = 0,
            guestsConcededCount = 0;
        for (Game game: fullStatGuests) {
            int matchResult = getWinDrawLoss(game, guests);
            if (matchResult == 1) guestsWinCount++;     // запись победы
            if (matchResult == 0) guestsDrawCount++;   // запись нечьи
            if (matchResult == -1) guestsLoseCount++; // запись поражения
            String scoreString = game.getScore();
            if (!scoreString.contains("-")) {
                guestsMatchsCount++; // Количество матчей
                String[] score = game.getScore().split(":");
                int ownersGoals = Integer.parseInt(score[0].trim());
                int guestsGoals = Integer.parseInt(score[1].trim());
                if (game.getTeam1() == guests) {                              // если играли дома
                    guestsGoalsCount = guestsGoalsCount + ownersGoals;       // запись забитых голов
                    guestsConcededCount = guestsConcededCount + guestsGoals;// запись пропущенных голов
                }
                else {                                                        // если играли в гостях
                    guestsGoalsCount = guestsGoalsCount + guestsGoals;       // запись забитых голов
                    guestsConcededCount = guestsConcededCount + ownersGoals;// запись пропущенных голов
                }
            }
        }
        result.addAll(Arrays.asList("Матчи", matchsCount.toString(), guestsMatchsCount.toString(),
                                    "Победы", winCount.toString(), guestsWinCount.toString(),
                                    "Ничьи", drawCount.toString(), guestsDrawCount.toString(),
                                    "Поражения", loseCount.toString(), guestsLoseCount.toString(),
                                    "Забито", goalsCount.toString(), guestsGoalsCount.toString(),
                                    "Пропущено", concededCount.toString(), guestsConcededCount.toString()));
        return result;
    }

    private int getWinDrawLoss(Game game, Team team) {
        try {
            String[] score = game.getScore().split(":");
            int ownersGoals = Integer.parseInt(score[0].trim());
            int guestsGoals = Integer.parseInt(score[1].trim());
            //если счет равный записываем ничью т.е. 0
            if (ownersGoals == guestsGoals) return 0;
            else {
                // если выйграла ком1 и ком1 это owners тогда записываем победу т.е. 1
                if (ownersGoals > guestsGoals && game.getTeam1() == team) return 1;
                else {
                    // если выйграла ком2 и ком2 это owners тогда записываем победу т.е. 1
                    if (ownersGoals < guestsGoals && game.getTeam2() == team) return 1;
                        // в остальных случаях поражение т.е. -1
                    else return -1;
                }
            }
        }
        catch (NumberFormatException e) {
            return 2;
        }
    }
}