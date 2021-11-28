package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class DeliveryService {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXt")
    private String orderReceiveEmail;
    @Column(columnDefinition = "TEXT")
    private String orderReceiveLink;

    @OneToMany(mappedBy = "deliveryService")
    private Set<DeliveryPoint> points;

    @OneToMany(mappedBy = "deliveryService")
    private Set<DeliveryMean> means;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryService that = (DeliveryService) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(orderReceiveEmail, that.orderReceiveEmail) && Objects.equals(orderReceiveLink, that.orderReceiveLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, orderReceiveEmail, orderReceiveLink);
    }
}
