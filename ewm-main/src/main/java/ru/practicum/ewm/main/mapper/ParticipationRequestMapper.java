package ru.practicum.ewm.main.mapper;

import ru.practicum.ewm.main.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.main.entity.ParticipationRequest;

import static ru.practicum.ewm.main.util.DateFormatter.format;

public class ParticipationRequestMapper {

    public static ParticipationRequestDto toDto(ParticipationRequest request) {
        if (request == null) {
            return null;
        }

        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(format(request.getCreated()))
                .eventId(request.getEvent() != null ? request.getEvent().getId() : null)
                .requesterId(request.getRequester() != null ? request.getRequester().getId() : null)
                .status(request.getStatus())
                .build();
    }
}
