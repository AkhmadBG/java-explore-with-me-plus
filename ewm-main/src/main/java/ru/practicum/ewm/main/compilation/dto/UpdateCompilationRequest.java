package ru.practicum.ewm.main.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UpdateCompilationRequest {

    @NotBlank
    @Size(min = 1, max = 50)
    private String title; // Заголовок подборки

    @NotNull
    private Boolean pinned; // Закреплена ли подборка на главной странице сайта

    @NotNull
    private Set<Long> events; // Список идентификаторов событий входящих в подборку

//    description:
//    Изменение информации о подборке событий. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
//
//    events	[
//    uniqueItems: true
//    Список id событий подборки для полной замены текущего списка
//
//    integer($int64)
//    Список id событий подборки для полной замены текущего списка
//
//]
//    pinned	boolean
//    example: true
//    Закреплена ли подборка на главной странице сайта
//
//    title	string
//    maxLength: 50
//    minLength: 1
//    example: Необычные фотозоны
//    Заголовок подборки

}
