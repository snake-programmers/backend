package com.alesharik.storemain.repository;

import com.alesharik.storemain.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
