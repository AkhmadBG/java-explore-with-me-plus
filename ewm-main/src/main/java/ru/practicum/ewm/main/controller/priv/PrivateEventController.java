package ru.practicum.ewm.main.controller.priv;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.dto.event.EventFullDto;
import ru.practicum.ewm.main.dto.event.EventShortDto;
import ru.practicum.ewm.main.dto.event.NewEventDto;
import ru.practicum.ewm.main.dto.event.UpdateEventUserDto;
import ru.practicum.ewm.main.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.main.dto.request.UpdateParticipationRequestDto;
import ru.practicum.ewm.main.service.event.EventService;
import ru.practicum.ewm.main.service.request.ParticipationRequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateEventController {

    private final EventService eventService;
    private final ParticipationRequestService participationRequestService;

    @GetMapping("/{userId}/events")
    public Page<EventShortDto> getEvents(@PathVariable Long userId,
                                         @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size) {
        return eventService.getEvents(userId, PageRequest.of(from, size));
    }

    //    POST /users/{userId}/events Добавление нового события
    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable Long userId,
                                    @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.createEvent(userId, newEventDto);
    }

    //    GET /users/{userId}/events/{eventId} Получение полной информации о событии добавленном текущим пользователем
    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEvent(@PathVariable Long userId,
                                 @PathVariable Long eventId) {
        return eventService.getEventByUser(userId, eventId);
    }

    //    PATCH /users/{userId}/events/{eventId} Изменение события добавленного текущим пользователем
    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable Long userId,
                                          @PathVariable Long eventId,
                                          @Valid @RequestBody UpdateEventUserDto updateEventUserDto) {
        return eventService.updateEventByUser(userId, eventId, updateEventUserDto);
    }

    //    GET /users/{userId}/events/{eventId}/requests Получение информации о запросах на участие в событии текущего пользователя
    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getUserRequestsByEventId(@PathVariable Long userId,
                                                                  @PathVariable Long eventId) {
        return participationRequestService.getUserRequestsByEventId(userId, eventId);
    }

    //    PATCH /users/{userId}/events/{eventId}/requests Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    @PatchMapping("/{userId}/events/{eventId}/requests")
    public ParticipationRequestDto updateUserRequestsByEventId(@PathVariable Long userId,
                                                               @PathVariable Long eventId,
                                                               @Valid @RequestBody UpdateParticipationRequestDto updateParticipationRequestDto) {
        return participationRequestService.updateUserRequestsByEventId(userId, eventId, updateParticipationRequestDto);
    }

}