package ru.statistic.football.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.statistic.football.app.Dto.ArchiveDto;
import ru.statistic.football.app.Entity.Archive;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArchiveMapper {

    ArchiveDto toDto(Archive archive);
    List<ArchiveDto> toDtoList(List<Archive> archiveList);
}
