package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public User create(User user) {
        validateUser(user);
        if (userStorage.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email уже существует");
        }
        return userStorage.create(user);
    }

    @Override
    public User update(Long userId, User user) {
        User existingUser = getById(userId);

        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            if (userStorage.existsByEmail(user.getEmail())) {
                throw new ConflictException("Email уже существует");
            }
            existingUser.setEmail(user.getEmail());
        }

        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }

        return userStorage.update(existingUser);
    }

    @Override
    public User getById(Long userId) {
        return userStorage.getById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    @Override
    public List<User> getAll() {
        return userStorage.getAll();
    }

    @Override
    public void delete(Long userId) {
        userStorage.delete(userId);
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Неверное email");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
    }
}