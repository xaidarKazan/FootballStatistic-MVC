package ru.statistic.football.app.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "season", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_season")
    private Long id_season;

    @Column(name = "value", nullable = false)
    private String value;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private List<Game> games = new ArrayList<>();
}
