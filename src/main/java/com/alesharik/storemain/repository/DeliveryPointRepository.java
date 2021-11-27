package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.DeliveryPoint;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DeliveryPointRepository extends PagingAndSortingRepository<DeliveryPoint, Long> {
}
