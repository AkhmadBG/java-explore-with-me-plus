package ru.practicum.ewm.main.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.dto.user.NewUserRequest;
import ru.practicum.ewm.main.dto.user.UserDto;
import ru.practicum.ewm.main.entity.User;
import ru.practicum.ewm.main.exception.ConflictException;
import ru.practicum.ewm.main.exception.NotFoundException;
import ru.practicum.ewm.main.mapper.UserMapper;
import ru.practicum.ewm.main.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        Pageable page = PageRequest.of(from / size, size);
        return repository.findAll(page)
                .map(UserMapper::toDto)
                .getContent();
    }

    @Override
    public UserDto createUser(NewUserRequest newUser) {
        String email = newUser.getEmail();
        if (repository.existsByEmail(email)) {
            throw new ConflictException("User with email=%s already exists.".formatted(email));
        }

        User user = UserMapper.toEntity(newUser);
        return UserMapper.toDto(repository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        if (!repository.existsById(userId)) {
            throw new NotFoundException("User with id=%d not found.".formatted(userId));
        }

        repository.deleteById(userId);
    }
}