package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User save(User user);

    Optional<User> findById(Long id);

    List<User> findAll();

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    void deleteById(Long id);
}