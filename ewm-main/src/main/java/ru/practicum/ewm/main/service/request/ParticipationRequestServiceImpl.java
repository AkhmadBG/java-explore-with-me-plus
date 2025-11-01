package ru.practicum.ewm.main.service.request;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.dto.request.ParticipationRequestDto;
import ru.practicum.ewm.main.dto.request.UpdateParticipationRequestDto;
import ru.practicum.ewm.main.entity.Event;
import ru.practicum.ewm.main.entity.ParticipationRequest;
import ru.practicum.ewm.main.entity.User;
import ru.practicum.ewm.main.enums.EventState;
import ru.practicum.ewm.main.enums.RequestStatus;
import ru.practicum.ewm.main.exception.*;
import ru.practicum.ewm.main.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.repository.ParticipationRequestRepository;
import ru.practicum.ewm.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
//        request.setStatus(event.getRequestModeration() == false ? RequestStatus.PENDING : RequestStatus.CONFIRMED);

        //тестирую логику
        if (event.getRequestModeration() && event.getParticipantLimit() != 0) {
            request.setStatus(RequestStatus.PENDING); // требуется модерация
        } else {
            request.setStatus(RequestStatus.CONFIRMED); // автоматическое подтверждение
            event.setConfirmedRequests(event.getConfirmedRequests() + 1); // увеличение счетчика
        }

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
        if (!userRepository.existsById(userId)) {
            throw new UserNotExistException("User with id=" + userId + " was not found");
        }
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Event with id=" + eventId + " was not found");
        }
        List<ParticipationRequest> requests = requestRepository.findAllByRequester_IdAndEvent_Id(userId, eventId);
        return requests.stream()
                .map(ParticipationRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto updateUserRequestsByEventId(Long userId, Long eventId, UpdateParticipationRequestDto updateDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " was not found"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("User is not the initiator of the event");
        }

        List<Long> requestIds = updateDto.getRequestsId();
        if (requestIds == null || requestIds.isEmpty()) {
            List<ParticipationRequest> pendingRequests = requestRepository.findByEventIdAndStatus(eventId, RequestStatus.PENDING);
            if (pendingRequests.isEmpty()) {
                throw new NotFoundException("No pending requests found for this event");
            }
            requestIds = pendingRequests.stream()
                    .map(ParticipationRequest::getId)
                    .collect(Collectors.toList());
        }

        List<ParticipationRequest> requests = requestRepository.findAllById(requestIds);

        if (requests.size() != requestIds.size()) {
            throw new NotFoundException("Some request IDs were not found");
        }

        for (ParticipationRequest request : requests) {
            if (!request.getEvent().getId().equals(eventId)) {
                throw new ForbiddenException("Request with id=" + request.getId() + " does not belong to this event");
            }

            if (updateDto.getStatus() == RequestStatus.CONFIRMED && request.getStatus() == RequestStatus.CONFIRMED) {
                throw new ConflictException("Request is already confirmed");
            }
        }

        if (updateDto.getStatus() == RequestStatus.CONFIRMED) {
            long currentConfirmed = event.getConfirmedRequests();
            long tryingToConfirm = requests.stream()
                    .filter(r -> r.getStatus() != RequestStatus.CONFIRMED)
                    .count();

            if (event.getParticipantLimit() > 0 &&
                    currentConfirmed + tryingToConfirm > event.getParticipantLimit()) {
                throw new ConflictException("Participant limit exceeded");
            }
        }

        RequestStatus newStatus = updateDto.getStatus();
        for (ParticipationRequest request : requests) {
            request.setStatus(newStatus);
        }

        List<ParticipationRequest> updatedRequests = requestRepository.saveAll(requests);

        updateEventConfirmedRequests(event, newStatus, requests);

        return ParticipationRequestMapper.toDto(updatedRequests.get(0));
    }

    private void updateEventConfirmedRequests(Event event, RequestStatus newStatus, List<ParticipationRequest> requests) {
        if (newStatus == RequestStatus.CONFIRMED) {
            Long confirmedCount = requestRepository.countByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED);
            event.setConfirmedRequests(confirmedCount);
            log.info("Updated confirmedRequests to {} based on DB count", confirmedCount);
        } else if (newStatus == RequestStatus.REJECTED) {
            Long confirmedCount = requestRepository.countByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED);
            event.setConfirmedRequests(confirmedCount);
        }

        eventRepository.save(event);
    }
}