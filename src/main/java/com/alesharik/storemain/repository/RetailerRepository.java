package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.Retailer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RetailerRepository extends PagingAndSortingRepository<Retailer, Long> {
    List<Retailer> findByNameContaining(String name);
}
