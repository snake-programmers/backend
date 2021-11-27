package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.DeliveryMean;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeliveryMeanDto {
    private final long id;
    private final String name;
    private final int minMeters;
    private final int maxMeters;
    private final int pricePerKm;
    private final DeliveryServiceDto deliveryService;

    public DeliveryMeanDto(DeliveryMean mean) {
        this.id = mean.getId();
        this.name = mean.getName();
        this.minMeters = mean.getMinMeters();
        this.maxMeters = mean.getMaxMeters();
        this.pricePerKm = mean.getPricePerKm();
        this.deliveryService = new DeliveryServiceDto(mean.getDeliveryService());
    }
}
