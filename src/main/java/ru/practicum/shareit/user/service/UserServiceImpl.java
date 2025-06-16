package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.user.*;
import ru.practicum.shareit.user.dto.*;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        validateUser(user);

        if (userStorage.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email уже используется");
        }

        User createdUser = userStorage.save(user);
        return convertToDto(createdUser);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User existingUser = userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (userDto.getEmail() != null && !userDto.getEmail().equals(existingUser.getEmail())) {
            if (userStorage.existsByEmail(userDto.getEmail())) {
                throw new ConflictException("Email уже используется");
            }
            existingUser.setEmail(userDto.getEmail());
        }

        if (userDto.getName() != null) {
            existingUser.setName(userDto.getName());
        }

        User updatedUser = userStorage.save(existingUser);
        return convertToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return convertToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userStorage.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userStorage.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        userStorage.deleteById(userId);
    }

    private User convertToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new BadRequestException("Некорректный email");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new BadRequestException("Имя не может быть пустым");
        }
    }
}