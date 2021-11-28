package com.alesharik.storemain.user.controller;

import com.alesharik.storemain.entity.Order;
import com.alesharik.storemain.repository.DeliveryMeanRepository;
import com.alesharik.storemain.repository.DeliveryPointRepository;
import com.alesharik.storemain.repository.OrderRepository;
import com.alesharik.storemain.user.security.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final DeliveryMeanRepository deliveryMeanRepository;
    private final DeliveryPointRepository deliveryPointRepository;
    private final GeometryFactory geometryFactory;

    @PostMapping("/user/order")
    public void createOrder(
            @RequestParam("mean") long mean,
            @RequestParam("point") long point,
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            @RequestParam("count") int count
    ) {
        var order = new Order();
        order.setMean(deliveryMeanRepository.findById(mean).get());
        order.setPoint(deliveryPointRepository.findById(point).get());
        order.setDestination(geometryFactory.createPoint(new CoordinateXY(lat, lon)));
        order.setCount(count);
        var u = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUser();
        order.setUser(u);
        order.setStatus("В доставке");
        orderRepository.save(order);
    }
}
