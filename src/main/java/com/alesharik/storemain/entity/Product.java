package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    private String volume;
    private String humanVolume;
    /**
     * / 100
     */
    private int price;
    private int itemsPerPack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producttype_id", nullable = false)
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "product")
    private Set<UserBasket> baskets;
}
