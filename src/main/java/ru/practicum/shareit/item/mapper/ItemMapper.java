package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        if (item == null) {
            return null;
        }

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequestId())
                .build();
    }

    public static Item toItem(ItemDto itemDto) {
        if (itemDto == null) {
            return null;
        }

        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(null) // Устанавливается отдельно
                .requestId(itemDto.getRequestId())
                .build();
    }

    public static ItemDto toItemDtoWithBooking(Item item, User owner) {
        if (item == null) {
            return null;
        }

        ItemDto itemDto = toItemDto(item);
        // Здесь можно добавить логику для полей бронирования, если нужно
        return itemDto;
    }
}