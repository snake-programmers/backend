package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.ProductType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductTypeRepository extends PagingAndSortingRepository<ProductType, Long> {
    Optional<ProductType> findByName(String name);
}
