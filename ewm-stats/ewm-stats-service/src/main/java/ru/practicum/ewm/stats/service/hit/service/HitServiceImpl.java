package ru.practicum.ewm.stats.service.hit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.dto.GetStatsDto;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.stats.dto.CreateHitDto;
import ru.practicum.ewm.stats.dto.ViewStats;
import ru.practicum.ewm.stats.service.hit.mapper.HitMapper;
import ru.practicum.ewm.stats.service.hit.model.Hit;
import ru.practicum.ewm.stats.service.hit.repository.HitRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {
    private final HitRepository hitRepository;

    @Override
    public HitDto create(CreateHitDto endPointHitCreateDto) {
        Hit createdHit = hitRepository.save(HitMapper.toHit(endPointHitCreateDto));
        return HitMapper.toEndPointHitDto(createdHit);
    }

    @Override
    public List<ViewStats> getStats(GetStatsDto dto) {
        if (dto.getUnique()) {
            return hitRepository.getViewStats(dto.getStart(), dto.getEnd(), dto.getUris());
        }

        return hitRepository.getViewStatsNonUnique(dto.getStart(), dto.getEnd(),  dto.getUris());
    }
}
