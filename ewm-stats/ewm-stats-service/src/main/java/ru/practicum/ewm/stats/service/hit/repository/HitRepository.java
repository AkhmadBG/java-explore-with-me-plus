package ru.practicum.ewm.stats.service.hit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.stats.service.hit.model.Hit;

public interface HitRepository extends JpaRepository<Hit, Long> {
}
