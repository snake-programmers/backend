package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.Store;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StoreRepository extends PagingAndSortingRepository<Store, Long> {
    List<Store> findByNameContaining(String name);
}
