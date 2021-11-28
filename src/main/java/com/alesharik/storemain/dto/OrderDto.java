package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.Order;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class OrderDto {
    private final long id;
    private final String deliveredBy;
    private final String deliveryService;
    private final String status;
    private final int count;
    private final Set<OrderItemDto> items;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.deliveredBy = order.getMean().getName();
        this.deliveryService = order.getMean().getDeliveryService().getName();
        this.status = order.getStatus();
        this.count = order.getCount();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toSet());
    }
}
