package ru.statistic.football.app.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "archive", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archive")
    private Long id_archive;

    @Column(name = "url", nullable = false)
    private String urlAddress;
}
