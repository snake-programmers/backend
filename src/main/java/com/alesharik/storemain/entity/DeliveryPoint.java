package com.alesharik.storemain.entity;

import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class DeliveryPoint {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    private String address;
    @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point location;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private DeliveryService deliveryService;
    @OneToMany(mappedBy = "point")
    private Set<Order> orders;
}
