package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(indexes = {
        @Index(columnList = "name")
})
public class ProductType {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    private String volume;
    private String humanVolume;
    @Column(columnDefinition = "TEXT")
    private String picture;
    @OneToMany(mappedBy = "productType")
    private Set<Product> products;
}
