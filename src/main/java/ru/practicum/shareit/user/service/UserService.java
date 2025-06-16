package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);  // Для работы с DTO

    UserDto updateUser(Long userId, UserDto userDto);

    UserDto getUserById(Long userId);

    List<UserDto> getAllUsers();

    void deleteUser(Long userId);
}