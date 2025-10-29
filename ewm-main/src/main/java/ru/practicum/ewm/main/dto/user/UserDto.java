package ru.practicum.ewm.main.dto.user;

import lombok.*;

/**
 * Полное описание пользователя.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
}