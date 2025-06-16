package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        Item item = ItemMapper.toItem(itemDto);
        return ItemMapper.toItemDto(this.create(item, userId));
    }

    @Override
    public Item create(Item item, Long userId) {
        UserDto userDto = userService.getUserById(userId); // Получаем UserDto
        User owner = UserMapper.toUser(userDto); // Преобразуем в User
        item.setOwner(owner);
        validateItem(item);
        return itemStorage.create(item);
    }

    @Override
    public ItemDto update(long itemId, long userId, ItemDto itemDto) {
        Item existingItem = getItemById(itemId, userId);

        if (itemDto.getName() != null) {
            existingItem.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            existingItem.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            existingItem.setAvailable(itemDto.getAvailable());
        }

        return ItemMapper.toItemDto(itemStorage.update(existingItem));
    }

    @Override
    public ItemDto getById(long itemId, long userId) {
        return ItemMapper.toItemDto(getItemById(itemId, userId));
    }

    @Override
    public List<ItemDto> getAllByOwner(long userId) {
        userService.getUserById(userId);
        return itemStorage.getAllByOwner(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isBlank()) {
            return List.of();
        }
        return itemStorage.search(text).stream()
                .filter(Item::getAvailable)
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private Item getItemById(long itemId, long userId) {
        userService.getUserById(userId);
        return itemStorage.getById(itemId)
                .orElseThrow(() -> new NotFoundException("Item not found"));
    }

    private void validateItem(Item item) {
        if (item.getName() == null || item.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (item.getAvailable() == null) {
            throw new IllegalArgumentException("Available status cannot be null");
        }
    }
}