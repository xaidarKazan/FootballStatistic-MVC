package ru.statistic.football.app.Service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.statistic.football.app.Entity.Season;
import ru.statistic.football.app.Service.api.SeasonSevice;
import ru.statistic.football.app.repository.SeasonRepository;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeasonSeviceImpl implements SeasonSevice {

    private final SeasonRepository seasonRepository;

    @Override
    public Season getSeason(Long id) {
        return seasonRepository.getOne(id);
    }

    @Override
    public Season getSeason(String value) {
        return seasonRepository.getByValue(value);
    }

    @Override
    public Season getLastSeason() {
        return seasonRepository.getLastSeason();
    }

    @Override
    @Transactional
    public void saveSeason(Season season) {
        if (!seasonRepository.existsByValue(season.getValue())) {
            seasonRepository.saveAndFlush(season);
        }
    }

    @Override
    public List<Season> getSeasons() {
        return seasonRepository.findAll();
    }
}
