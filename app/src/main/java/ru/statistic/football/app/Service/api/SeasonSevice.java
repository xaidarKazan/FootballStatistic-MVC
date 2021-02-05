package ru.statistic.football.app.Service.api;

import ru.statistic.football.app.Entity.Season;

import java.util.List;

public interface SeasonSevice {
    Season getSeason(Long id);
    Season getSeason(String value);
    Season getLastSeason();
    void saveSeason(Season season);
    List<Season> getSeasons();
}
