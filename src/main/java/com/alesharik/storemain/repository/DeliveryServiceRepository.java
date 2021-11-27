package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.DeliveryService;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeliveryServiceRepository extends PagingAndSortingRepository<DeliveryService, Long> {
    List<DeliveryService> findByNameContaining(String input);
}
