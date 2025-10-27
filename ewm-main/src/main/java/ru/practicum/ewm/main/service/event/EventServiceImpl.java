package ru.practicum.ewm.main.service.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.dto.event.*;
import ru.practicum.ewm.main.entity.Category;
import ru.practicum.ewm.main.entity.Event;
import ru.practicum.ewm.main.entity.Location;
import ru.practicum.ewm.main.entity.User;
import ru.practicum.ewm.main.enums.EventState;
import ru.practicum.ewm.main.enums.SortValue;
import ru.practicum.ewm.main.exception.CategoryNotExistException;
import ru.practicum.ewm.main.exception.UserNotExistException;
import ru.practicum.ewm.main.exception.WrongTimeException;
import ru.practicum.ewm.main.mapper.EventMapper;
import ru.practicum.ewm.main.repository.CategoryRepository;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.main.util.DateFormatter.format;
import static ru.practicum.ewm.main.util.DateFormatter.parse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    // POST /users/{userId}/events
    @Override
    @Transactional
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException("User with id=" + userId + " was not found"));

        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new CategoryNotExistException("Category with id=" + newEventDto.getCategory() +
                        " was not found"));

        LocalDateTime eventDate = parse(newEventDto.getEventDate());
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new WrongTimeException("Field: eventDate. Error: должно содержать дату, которая еще не наступила. " +
                    "Value: " + eventDate);
        }

        Location location = new Location();
        location.setLat(newEventDto.getLocation().getLat());
        location.setLon(newEventDto.getLocation().getLon());

        Event event = EventMapper.toEvent(newEventDto, category, user, location);
        Event savedEvent = eventRepository.save(event);

        return EventMapper.toEventFullDto(savedEvent);
    }

    // GET /users/{userId}/events
    @Override
    public Page<EventShortDto> getEvents(Long userId, Pageable pageable) {
        if (!userRepository.existById(userId)) {
            throw new UserNotExistException("User with id=" + userId + " was not found");
        }

        Page<Event> eventsPage = eventRepository.findAllByInitiator(userId, pageable);
        if (eventsPage.hasContent()) {
            setView(eventsPage.getContent());
        }
        return eventsPage.map(EventMapper::toEventShortDto);
    }

    // PATCH /users/{userId}/events/{eventId}
    @Override
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserDto updateEventUserDto) {
        return null;
    }

    // GET /users/{userId}/events/{eventId}
    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        return null;
    }

    // PATCH /admin/events/{eventId}
    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminDto updateEventAdminDto) {
        return null;
    }

    // GET /events/{id}
    @Override
    public EventFullDto getEvent(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public void setView(List<Event> events) {

    }

    // GET /admin/events
    @Override
    public List<EventFullDto> getEventsWithParamsByAdmin(List<Long> users, EventState states, List<Long> categoriesId, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return List.of();
    }

    // GET /events
    @Override
    public List<EventFullDto> getEventsWithParamsByUser(String text, List<Long> categories, Boolean paid, String rangeStart, String rangeEnd, Boolean onlyAvailable, SortValue sort, Integer from, Integer size, HttpServletRequest request) {
        return List.of();
    }
}
