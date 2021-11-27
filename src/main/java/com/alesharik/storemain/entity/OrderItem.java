package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    private String volume;
    private String humanVolume;
    private float count;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
