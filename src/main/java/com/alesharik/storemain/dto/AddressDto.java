package com.alesharik.storemain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddressDto {
    private final String name;
    private final double lon;
    private final double lat;
}
