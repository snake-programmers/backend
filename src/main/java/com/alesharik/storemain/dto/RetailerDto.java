package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.Retailer;
import lombok.Data;

@Data
public class RetailerDto {
    private final long id;
    private final String name;
    private final String logo;

    public RetailerDto(Retailer retailer) {
        this.id = retailer.getId();
        this.name = retailer.getName();
        this.logo = retailer.getLogo();
    }
}
