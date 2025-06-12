package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    User update(User user);

    Optional<User> getById(Long userId);

    List<User> getAll();

    void delete(Long userId);

    boolean existsByEmail(String email);
}