package ru.statistic.football.app.Service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.statistic.football.app.Entity.Team;
import ru.statistic.football.app.Service.api.TeamService;
import ru.statistic.football.app.repository.TeamRepository;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Team getTeam(Long id) {
        return teamRepository.getOne(id);
    }

    @Override
    public Team getTeam(String name) {
        return teamRepository.getByName(name);
    }

    @Override
    @Transactional
    public void saveTeam(Team team) {
        if(!teamRepository.existsByName(team.getName())) {
            teamRepository.saveAndFlush(team);
        }
    }
}