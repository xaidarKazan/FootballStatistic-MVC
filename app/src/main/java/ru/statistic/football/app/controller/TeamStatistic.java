package ru.statistic.football.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.statistic.football.app.Service.api.StatisticService;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TeamStatistic {

    private final StatisticService statisticService;

    @GetMapping("/teamStatistic")
    public String getPage(Model model) {
        return "teamStatistic";
    }


    @GetMapping("/statistics")
    public String getTeamStat(@RequestParam String team1, @RequestParam String team2, Map<String, Object> model) {

        model.put("owners", team1);
        model.put("guests", team2);

        // Табличка с процентами побед и поражений в личных встречах команд
        List<Integer> firstTable = statisticService.getStatWinOwnersDrawWinGuests(team1, team2);
        model.put("ownersWin", firstTable.get(0));
        model.put("graw", firstTable.get(2));
        model.put("guestsWin", firstTable.get(1));

        // Табличка результата последних 6 встречей команд
        List<String> secTabOwners = statisticService.getStatisticsOfTheLast6Matches(team1);
        List<String> secTabGuests = statisticService.getStatisticsOfTheLast6Matches(team2);
        model.put("secTabOwners", secTabOwners);
        model.put("secTabGuests", secTabGuests);

        // Табличка полной статистики за сезон
        List<String> threeTabStats = statisticService.getFullSeasonStatistics(team1, team2);
        model.put("oMatches", threeTabStats.get(1));
        model.put("oWins", threeTabStats.get(4));
        model.put("oDraws", threeTabStats.get(7));
        model.put("oLoses", threeTabStats.get(10));
        model.put("oGoals", threeTabStats.get(13));
        model.put("oMisseds", threeTabStats.get(16));

        model.put("gMatches", threeTabStats.get(2));
        model.put("gWins", threeTabStats.get(5));
        model.put("gDraws", threeTabStats.get(8));
        model.put("gLoses", threeTabStats.get(11));
        model.put("gGoals", threeTabStats.get(14));
        model.put("gMisseds", threeTabStats.get(17));

        return "statistics";
    }

}
