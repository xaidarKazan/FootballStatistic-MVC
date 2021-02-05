package ru.statistic.football.app.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "game", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_game")
    private Long id_game;

    @Column(name = "tour", nullable = false)
    private Integer tour;

    @ManyToOne
    @JoinColumn(name = "id_season")
    private Season season;

    @ManyToOne
    @JoinColumn(name = "id_team_1")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "id_team_2")
    private Team team2;

    @Column(name = "score")
    private String score;

    @Column(name = "date")
    private LocalDate date;
}
