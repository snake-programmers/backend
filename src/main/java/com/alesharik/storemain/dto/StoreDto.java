package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.Store;
import lombok.Data;

@Data
public class StoreDto {
    private final long id;
    private final String name;
    private final String address;
    private final RetailerDto retailer;

    public StoreDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.address = store.getAddress();
        this.retailer = new RetailerDto(store.getRetailer());
    }
}
