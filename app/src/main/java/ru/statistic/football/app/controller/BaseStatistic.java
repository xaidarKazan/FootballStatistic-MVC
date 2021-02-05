package ru.statistic.football.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.statistic.football.app.Service.api.BaseService;

@Controller
@RequestMapping("/baseStatistic")
@RequiredArgsConstructor
public class BaseStatistic {

    private final BaseService baseService;

    @GetMapping
    public String getStart(Model model) {
        model.addAttribute("urlArchives", baseService.getUrlArchives().toString());
        return "baseStatistic";
    }

    @PostMapping
    public String addUrlAddress(@RequestParam String textUrl, Model model) {
        model.addAttribute("urlArchives", baseService.saveUrl(textUrl).toString());
        return "baseStatistic";
    }
}
