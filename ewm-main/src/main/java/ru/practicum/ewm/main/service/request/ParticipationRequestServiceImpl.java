package ru.practicum.ewm.main.service.request;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.main.dto.request.UpdateParticipationRequestDto;
import ru.practicum.ewm.main.entity.Event;
import ru.practicum.ewm.main.entity.ParticipationRequest;
import ru.practicum.ewm.main.entity.User;
import ru.practicum.ewm.main.enums.EventState;
import ru.practicum.ewm.main.enums.RequestStatus;
import ru.practicum.ewm.main.exception.ConflictException;
import ru.practicum.ewm.main.exception.ForbiddenException;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.repository.ParticipationRequestRepository;
import ru.practicum.ewm.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ParticipationRequestRepository requestRepository;

    @Transactional
    public List<ParticipationRequest> getUserRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=%d was not found".formatted(userId)));
        return requestRepository.findByRequesterId(userId);
    }

    @Transactional
    public ParticipationRequest addRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=%d was not found".formatted(userId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=%d was not found".formatted(eventId)));

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Initiator cannot request own event");
        }

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Event is not published");
        }

        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException("Request already exists");
        }

        if (event.getParticipantLimit() > 0 && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Participant limit reached");
        }

        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(user);
        request.setEvent(event);
        request.setCreated(LocalDateTime.now());
        // ... если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
        request.setStatus(event.getRequestModeration() == false ? RequestStatus.PENDING : RequestStatus.CONFIRMED);

        return requestRepository.save(request);
    }

    @Transactional
    public ParticipationRequest cancelRequest(Long userId, Long requestId) {
        ParticipationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id=%d was not found".formatted(requestId)));

        if (!request.getRequester().getId().equals(userId)) {
            throw new ForbiddenException("Cannot cancel another user's request");
        }

        request.setStatus(RequestStatus.CANCELED);
        return requestRepository.save(request);
    }

    @Override
    public List<ParticipationRequestDto> getUserRequestsByEventId(Long userId, Long eventId) {
        return List.of();
    }

    @Override
    public ParticipationRequestDto updateUserRequestsByEventId(Long userId, Long eventId, UpdateParticipationRequestDto updateParticipationRequestDto) {
        return null;
    }

}