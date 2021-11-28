package com.alesharik.storemain.user.controller;

import com.alesharik.storemain.dto.ProductTypeDto;
import com.alesharik.storemain.entity.NearestProductDto;
import com.alesharik.storemain.repository.ProductRepository;
import com.alesharik.storemain.repository.ProductTypeRepository;
import com.alesharik.storemain.user.security.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
public class UserProductController {
    private static final int MAX_DISTANCE_METERS = 500_000;
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;

    @GetMapping("/user/products/search")
    public List<ProductTypeDto> searchProducts(@RequestParam(value = "q", defaultValue = "") String text) {
        if (text.isBlank()) {
            return StreamSupport.stream(productTypeRepository.findAll().spliterator(), false)
                    .map(ProductTypeDto::new)
                    .collect(Collectors.toList());
        } else {
            return productTypeRepository.findAllByNameContaining(text.toLowerCase())
                    .stream()
                    .map(ProductTypeDto::new)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/user/products/nearest")
    public List<NearestProductDto> nearestProducts(@RequestParam("type") long type) {
        var user = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUser();
        return productRepository.findAllNearestByProductType(type, user.getHomeLocation())
                .stream()
                .filter(projection -> projection.getDistance() < MAX_DISTANCE_METERS)
                .map(NearestProductDto::new)
                .collect(Collectors.toList());
    }
}
