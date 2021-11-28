package com.alesharik.storemain.entity;

import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "geometry(Point,4326)", nullable = false)
    private Point homeLocation;
    @Column(columnDefinition = "TEXT")
    private String homeAddress;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
}
