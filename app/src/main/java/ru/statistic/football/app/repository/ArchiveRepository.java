package ru.statistic.football.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.statistic.football.app.Entity.Archive;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    boolean existsByUrlAddress(String textUrl);
}
