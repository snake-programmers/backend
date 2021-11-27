package com.alesharik.storemain.admin.service;

import com.alesharik.storemain.entity.Product;
import com.alesharik.storemain.entity.ProductType;
import com.alesharik.storemain.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    public ProductType getProductType(Product product) {
        var existing = productTypeRepository.findByName(product.getName().toLowerCase());
        return existing.orElseGet(() -> {
            var type = new ProductType();
            type.setName(product.getName().toLowerCase());
            type.setHumanVolume(product.getHumanVolume());
            type.setVolume(product.getVolume());
            return productTypeRepository.save(type);
        });
    }
}
