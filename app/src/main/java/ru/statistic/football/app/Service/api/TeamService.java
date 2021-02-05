package ru.statistic.football.app.Service.api;

import ru.statistic.football.app.Entity.Team;

public interface TeamService {
    Team getTeam(Long id);
    Team getTeam(String name);
    void saveTeam(Team team);
}
