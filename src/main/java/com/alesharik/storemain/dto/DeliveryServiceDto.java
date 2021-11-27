package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.DeliveryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeliveryServiceDto {
    private final long id;
    private final String name;
    private final String orderReceiveEmail;
    private final String orderReceiveLink;

    public DeliveryServiceDto(DeliveryService deliveryService) {
        this.id = deliveryService.getId();
        this.name = deliveryService.getName();
        this.orderReceiveEmail = deliveryService.getOrderReceiveEmail();
        this.orderReceiveLink = deliveryService.getOrderReceiveLink();
    }
}
