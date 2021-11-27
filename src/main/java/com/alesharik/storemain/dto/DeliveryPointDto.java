package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.DeliveryPoint;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeliveryPointDto {
    private final long id;
    private final String name;
    private final String address;
    private final double lat;
    private final double lon;
    private final DeliveryServiceDto deliveryService;

    public DeliveryPointDto(DeliveryPoint point) {
        this.id = point.getId();
        this.name = point.getName();
        this.address = point.getAddress();
        this.lat = point.getLocation().getX();
        this.lon = point.getLocation().getY();
        this.deliveryService = new DeliveryServiceDto(point.getDeliveryService());
    }
}
