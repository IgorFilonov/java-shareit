package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    Item create(Item item);

    Item update(Item item);

    Optional<Item> getById(Long itemId);

    List<Item> getAllByOwner(Long ownerId);

    List<Item> search(String text);
}