package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class DeliveryMean {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    private int minMeters;
    private int maxMeters;
    /**
     * / 100
     */
    private int pricePerKm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private DeliveryService deliveryService;
    @OneToMany(mappedBy = "mean")
    private Set<Order> orders;
}
