package ru.statistic.football.app.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveDto {

    private String urlAddress;

    @Override
    public String toString() {
        return urlAddress;
    }
}