package com.alesharik.storemain.user.controller;

import com.alesharik.storemain.dto.OrderDto;
import com.alesharik.storemain.repository.OrderRepository;
import com.alesharik.storemain.user.security.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserOrdersController {
    private final OrderRepository orderRepository;

    @GetMapping("/user/orders")
    public List<OrderDto> getOrders() {
        var user = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUser();
        var orders = orderRepository.findAllByUser(user);
        return orders.stream().map(OrderDto::new).collect(Collectors.toList());
    }
}
