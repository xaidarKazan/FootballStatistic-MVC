package ru.statistic.football.app.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_team")
    private Long id_team;
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "team1", cascade = CascadeType.ALL)
    private List<Game> homeGames = new ArrayList<>();

    @OneToMany(mappedBy = "team2", cascade = CascadeType.ALL)
    private List<Game> guestGames = new ArrayList<>();

}
