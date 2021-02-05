package ru.statistic.football.app.Service.api;

import java.util.List;

public interface StatisticService {
    List<Integer> getStatWinOwnersDrawWinGuests(String team1, String team2);
    List<String> getStatisticsOfTheLast6Matches(String team1);
    List<String> getFullSeasonStatistics(String team1, String team2);
}
