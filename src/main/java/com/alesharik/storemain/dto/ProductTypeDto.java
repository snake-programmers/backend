package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.ProductType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductTypeDto {
    private long id;
    private String name;
    private String volume;
    private String humanVolume;

    public ProductTypeDto(ProductType type) {
        this.id = type.getId();
        this.name = type.getName();
        this.volume = type.getVolume();
        this.humanVolume = type.getHumanVolume();
    }
}
