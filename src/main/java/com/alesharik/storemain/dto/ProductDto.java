package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductDto {
    private final long id;
    private final String name;
    private final String volume;
    private final String humanVolume;
    private final int price;
    private final int itemsPerPack;
    private final String storeName;
    private final String picture;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.volume = product.getVolume();
        this.humanVolume = product.getHumanVolume();
        this.price = product.getPrice();
        this.picture = product.getPicture();
        this.itemsPerPack = product.getItemsPerPack();
        this.storeName = product.getStore().getName();
    }
}
