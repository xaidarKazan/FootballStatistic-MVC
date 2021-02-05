package ru.statistic.football.app.Service.implementation;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.statistic.football.app.Dto.ArchiveDto;
import ru.statistic.football.app.Entity.Archive;
import ru.statistic.football.app.Entity.Game;
import ru.statistic.football.app.Entity.Season;
import ru.statistic.football.app.Entity.Team;
import ru.statistic.football.app.Service.api.BaseService;
import ru.statistic.football.app.Service.api.GameSevice;
import ru.statistic.football.app.Service.api.SeasonSevice;
import ru.statistic.football.app.Service.api.TeamService;
import ru.statistic.football.app.Service.parsing.UrlAdress;
import ru.statistic.football.app.mapper.ArchiveMapper;
import ru.statistic.football.app.repository.ArchiveRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseServiceImpl implements BaseService {

    private final ArchiveRepository archiveRepository;
    private final ArchiveMapper archiveMapper;
    private final SeasonSevice seasonSevice;
    private final TeamService teamService;
    private final GameSevice gameSevice;

    @Override
    public List<ArchiveDto> getUrlArchives() {
        return archiveMapper.toDtoList(archiveRepository.findAll());
    }

    @Override
    @Transactional
    public List<Archive> saveUrl(String newUrlAddress) {

        for (int i = 1; i < 39; i++) {
            if (!parse(newUrlAddress, i)) break;
        }

//      проверка на соответствие шаблона https://gol.ru/football/tournament/apl/
        if (newUrlAddress.contains("https://gol.ru/football/tournament/apl/")) {
            Archive archive = new Archive();
            archive.setUrlAddress(newUrlAddress);
            if (!archiveRepository.existsByUrlAddress(newUrlAddress)) {
                archiveRepository.save(archive);
            }
        }

        return archiveRepository.findAll();
    }

    public boolean parse(String urlText, Integer tour) {
        // номер созона
        String seasonValue = urlText.replaceFirst("https://gol.ru/football/tournament/apl/", "")
                .replaceFirst("/.+","") ;
        Season season =new Season();
        season.setValue(seasonValue);
        seasonSevice.saveSeason(season);

        try {
            URL url = new URL(urlText + tour + "_tour");
            System.out.println(url.toString());
            if (!UrlAdress.isExist(url)) {
                return false;
            }

            Document doc = Jsoup.parse(url,5000);
            Element elemente;

            //до 20-21 идет такой <div ... id=#tabs-results .....>, после меняется
            if (doc.selectFirst("#tabs-results > div.game-calendar ") == null) {
                elemente = doc.selectFirst("#tabs-calendar > div.game-calendar ");
            }
            else {
                //вытаскиваем необходимую область html
                elemente = doc.selectFirst("#tabs-results > div.game-calendar ");
            }

            // берем дочерние <div> из той области которую выделили
            Elements elements = elemente.children();

            for (int i = 0; i < elements.size(); i++){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String[] date = elements.eq(i).select("div.teaser-event__wrapper > div.teaser-event__left > div > div").text().split(" ");
                // Дата матча
                LocalDate matchDate = LocalDate.parse(date[0], formatter);

                String team1Name = elements.eq(i).select("div.teaser-event__wrapper > div.teaser-event__left > a > div.event-player__name").text();
                Team team1 = new Team();
                team1.setName(team1Name);
                teamService.saveTeam(team1); // сохранение 1 команды
                // счет матча
                String score = elements.eq(i).select("div.teaser-event__wrapper > div.teaser-event__board > a > div").text().trim();

                String team2Name = elements.eq(i).select("div.teaser-event__wrapper > div.teaser-event__right > a > div.event-player__name.event-player__name--order").text();
                Team team2 = new Team();
                team2.setName(team2Name);
                teamService.saveTeam(team2);  //сохранение 2 команды

                Game game = new Game();
                game.setSeason(seasonSevice.getSeason(seasonValue));
                game.setTeam1(teamService.getTeam(team1Name));
                game.setTeam2(teamService.getTeam(team2Name));
                game.setScore(score);
                game.setDate(matchDate);
                game.setTour(tour);
                if (!score.contains("-"))gameSevice.saveGame(game);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}