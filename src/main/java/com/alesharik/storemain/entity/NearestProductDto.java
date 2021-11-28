package com.alesharik.storemain.entity;

import com.alesharik.storemain.repository.projections.NearestProductProjection;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NearestProductDto {
    private final long productId;
    private final String storeAddress;
    private final String storeName;
    private final long storeId;
    private final int price;
    /**
     * In meters
     */
    private final double distance;

    public NearestProductDto(NearestProductProjection projection) {
        this.productId = projection.getId();
        this.storeAddress = projection.getAddress();
        this.storeName = projection.getStore();
        this.storeId = projection.getStoreid();
        this.price = projection.getPrice();
        this.distance = projection.getDistance();
    }
}
