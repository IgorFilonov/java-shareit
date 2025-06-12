package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    Item create(Item item, Long userId);

    Item update(Long itemId, Item item, Long userId);

    Item getById(Long itemId, Long userId);

    List<Item> getAllByOwner(Long userId);

    List<Item> search(String text);
}