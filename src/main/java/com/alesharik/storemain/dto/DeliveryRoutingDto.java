package com.alesharik.storemain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeliveryRoutingDto {
    private final double distance;
    private final String mean;
    private final long meanId;
    private final String service;
    private final long serviceId;
    private final int pricePerKm;
    private final long pointId;
}
