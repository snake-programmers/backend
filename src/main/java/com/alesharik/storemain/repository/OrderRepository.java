package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.Order;
import com.alesharik.storemain.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
