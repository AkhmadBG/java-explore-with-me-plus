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
import ru.practicum.ewm.main.exception.*;
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
            throw new WrongTimeException("Event date must be at least 2 hours from now" + eventDate);
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
    @Transactional
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserDto updateEventUserDto) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new EventNotExistException("Event with id=" + eventId + " was not found"));

        if (event.getPublishedOn() != null) {
            throw new AlreadyPublishedException("Cannot update published event");
        }

        if (updateEventUserDto == null) {
            return EventMapper.toEventFullDto(event);
        }

        updateEventFieldsFromUserDto(event, updateEventUserDto);

        if (updateEventUserDto.getStateAction() != null) {
            switch(updateEventUserDto.getStateAction()) {
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELLED);
                    break;
            }
        }
        Event updatedEvent = eventRepository.save(event);
        return EventMapper.toEventFullDto(updatedEvent);
    }

    // GET /users/{userId}/events/{eventId}
    @Override
    public EventFullDto getEventByUser(Long userId, Long eventId) {
        Event event = eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new EventNotExistException("Event with id=" + eventId + " was not found"));

        setView(List.of(event));
        return EventMapper.toEventFullDto(event);
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

    private void updateEventFieldsFromUserDto(Event event, UpdateEventUserDto dto) {
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }

        if (dto.getCategory() != null) {
            Category category = categoryRepository.findById(newEventDto.getCategory())
                    .orElseThrow(() -> new CategoryNotExistException("Category with id=" + newEventDto.getCategory() +
                            " was not found"));
            event.setCategory(category);
        }

        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }

        if (dto.getEventDate() != null) {
            LocalDateTime newEventDate = parse(dto.getEventDate());
            if (newEventDate.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new WrongTimeException("Event date must be at least 2 hours from now" + newEventDate);
            }
            event.setEventDate(newEventDate);
        }

        if (dto.getLocation() != null) {
            Location location = new Location();
            location.setLat(dto.getLocation().getLat());
            location.setLon(dto.getLocation().getLon());
            event.setLocation(location);
        }

        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }

        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }

        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }

        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
    }
}
