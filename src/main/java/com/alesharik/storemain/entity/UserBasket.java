package com.alesharik.storemain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserBasket {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private int count;
}
