package com.Yua.FastDelivery.Delivery_App.Mapping;

import com.Yua.FastDelivery.Delivery_App.Dto.ItemDto;
import com.Yua.FastDelivery.Delivery_App.Entity.ItemEntity;
import com.Yua.FastDelivery.Delivery_App.Entity.UserEntity;

public class ItemMapping {

    public static ItemDto mapToDto(ItemEntity entity) {
        if (entity == null) return null;

        return new ItemDto(
                entity.getName(),
                entity.getDescription(),
                entity.getAmount(),
                entity.getRating(),
                entity.getStock()
        );
    }

    public static ItemEntity mapToEntity(ItemDto dto) {
        if (dto == null) return null;

        ItemEntity entity = new ItemEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAmount(dto.getAmount());
        entity.setRating(dto.getRating());
        entity.setStock(dto.getStock());
        return entity;
    }
}
