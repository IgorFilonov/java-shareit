package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.practicum.shareit.validation.Create;

@Data
@Builder
public class ItemDto {
    private Long id;

    @NotBlank(groups = {Create.class}, message = "Название не может быть пустым")
    private String name;

    @NotBlank(groups = {Create.class}, message = "Описание не может быть пустым")
    private String description;

    @NotNull(groups = {Create.class}, message = "Статус доступности должен быть указан")
    private Boolean available;

    private Long requestId;
}