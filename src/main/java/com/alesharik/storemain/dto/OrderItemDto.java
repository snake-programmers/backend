package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.OrderItem;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderItemDto {
    private final long id;
    private final String name;
    private final String volume;
    private final String humanVolume;
    private final float count;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.name = orderItem.getName();
        this.volume = orderItem.getVolume();
        this.humanVolume = orderItem.getHumanVolume();
        this.count = orderItem.getCount();
    }
}
