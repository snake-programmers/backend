package com.alesharik.storemain.entity;

import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(homeLocation, user.homeLocation) && Objects.equals(homeAddress, user.homeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, homeLocation, homeAddress);
    }
}
