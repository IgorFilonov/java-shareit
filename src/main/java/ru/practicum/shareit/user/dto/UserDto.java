package ru.practicum.shareit.user.dto;

import lombok.*;
import ru.practicum.shareit.validation.Create;
import ru.practicum.shareit.validation.Update;

import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Null(groups = Create.class, message = "ID должен быть null при создании")
    @NotNull(groups = Update.class, message = "ID не может быть null при обновлении")
    private Long id;

    @NotBlank(groups = Create.class, message = "Имя не может быть пустым")
    @Size(max = 255, message = "Имя не может быть длиннее 255 символов")
    private String name;

    @NotBlank(groups = Create.class, message = "Email не может быть пустым")
    @Email(groups = {Create.class, Update.class}, message = "Некорректный формат email")
    @Size(max = 512, message = "Email не может быть длиннее 512 символов")
    private String email;
}