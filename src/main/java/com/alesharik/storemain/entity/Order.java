package com.alesharik.storemain.entity;

import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "mean_id", nullable = false)
    private DeliveryMean mean;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id", nullable = false)
    private DeliveryPoint point;
    @Column(nullable = false)
    private String status;
    @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point destination;
    private int count;
    @OneToMany(mappedBy = "order")
    private Set<OrderItem> items;


}
