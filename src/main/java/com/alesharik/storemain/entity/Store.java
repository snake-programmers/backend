package com.alesharik.storemain.entity;

import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Store {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point location;

    @ManyToOne
    @JoinColumn(name = "retailer_id", nullable = false)
    private Retailer retailer;

    @OneToMany(mappedBy = "store")
    private Set<Product> products;
}
