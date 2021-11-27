package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Retailer {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String logo;

    @OneToMany(mappedBy = "retailer")
    private Set<Store> stores;
}
