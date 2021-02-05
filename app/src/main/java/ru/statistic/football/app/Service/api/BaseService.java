package ru.statistic.football.app.Service.api;

import ru.statistic.football.app.Dto.ArchiveDto;
import ru.statistic.football.app.Entity.Archive;

import java.util.List;

public interface BaseService {
    List<ArchiveDto> getUrlArchives();
    List<Archive> saveUrl(String newUrlAddress);
}
