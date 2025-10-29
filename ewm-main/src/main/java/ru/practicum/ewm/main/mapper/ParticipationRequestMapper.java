package ru.practicum.ewm.main.mapper;

import ru.practicum.ewm.main.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.main.entity.ParticipationRequest;

import java.time.format.DateTimeFormatter;

public class ParticipationRequestMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequestDto toDto(ParticipationRequest pr) {
        ParticipationRequestDto dto = new ParticipationRequestDto();
        dto.setId(pr.getId());
        dto.setEvent(pr.getEvent().getId());
        dto.setRequester(pr.getRequester().getId());
        dto.setStatus(pr.getStatus().name());
        dto.setCreated(pr.getCreated().format(formatter));
        return dto;
    }
}
