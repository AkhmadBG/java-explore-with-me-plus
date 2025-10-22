package ru.practicum.ewm.stats.service.hit.mapper;

import ru.practicum.ewm.stats.dto.CreateHitDto;
import ru.practicum.ewm.stats.dto.HitDto;
import ru.practicum.ewm.stats.service.hit.model.Hit;

public class EndPointHitMapper {
    public static Hit toHit(CreateHitDto endPointHitCreateDto) {
        return Hit.builder()
                .app(endPointHitCreateDto.getApp())
                .uri(endPointHitCreateDto.getUri())
                .ip(endPointHitCreateDto.getIp())
                .timestamp(endPointHitCreateDto.getTimestamp())
                .build();
    }

    public static HitDto toEndPointHitDto(Hit endPointHit) {
        return HitDto.builder()
                .id(endPointHit.getId())
                .app(endPointHit.getApp())
                .uri(endPointHit.getUri())
                .ip(endPointHit.getIp())
                .timestamp(endPointHit.getTimestamp())
                .build();
    }
}
