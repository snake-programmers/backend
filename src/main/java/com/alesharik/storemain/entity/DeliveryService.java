package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;
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
}
