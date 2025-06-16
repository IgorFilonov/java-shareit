package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto create(long userId, ItemDto itemDto);  // Для работы с DTO

    Item create(Item item, Long userId);          // Для внутренней работы

    ItemDto update(long itemId, long userId, ItemDto itemDto);

    ItemDto getById(long itemId, long userId);

    List<ItemDto> getAllByOwner(long userId);

    List<ItemDto> search(String text);
}