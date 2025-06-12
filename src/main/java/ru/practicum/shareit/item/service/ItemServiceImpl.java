package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    @Override
    public Item create(Item item, Long userId) {
        userService.getById(userId);
        validateItem(item);
        return itemStorage.create(item);
    }

    @Override
    public Item update(Long itemId, Item item, Long userId) {
        Item existingItem = getById(itemId, userId);

        if (item.getName() != null) {
            existingItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            existingItem.setAvailable(item.getAvailable());
        }

        return itemStorage.update(existingItem);
    }

    @Override
    public Item getById(Long itemId, Long userId) {
        userService.getById(userId);
        return itemStorage.getById(itemId)
                .orElseThrow(() -> new NotFoundException("Элемент не найден"));
    }

    @Override
    public List<Item> getAllByOwner(Long userId) {
        User owner = userService.getById(userId);
        List<Item> items = itemStorage.getAllByOwner(userId);
        System.out.println("[DEBUG] Found " + items.size() + " элементы для пользователя " + userId);
        return items;
    }

    @Override
    public List<Item> search(String text) {
        if (text.isBlank()) {
            return List.of();
        }
        return itemStorage.search(text).stream()
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
    }

    private void validateItem(Item item) {
        if (item.getName() == null || item.getName().isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new IllegalArgumentException("Описание не может быть пустым");
        }
        if (item.getAvailable() == null) {
            throw new IllegalArgumentException("Статус не может быть нулевым");
        }
    }
}