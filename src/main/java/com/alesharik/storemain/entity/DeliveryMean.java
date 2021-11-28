package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryMean that = (DeliveryMean) o;
        return id == that.id && minMeters == that.minMeters && maxMeters == that.maxMeters && pricePerKm == that.pricePerKm && Objects.equals(name, that.name) && Objects.equals(deliveryService, that.deliveryService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, minMeters, maxMeters, pricePerKm, deliveryService);
    }
}
