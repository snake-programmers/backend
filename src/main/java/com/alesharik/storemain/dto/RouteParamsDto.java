package com.alesharik.storemain.dto;

import lombok.Data;

@Data
public class RouteParamsDto {
    private double lat;
    private double lon;
    private long[] stores;
}
