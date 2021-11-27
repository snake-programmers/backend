package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.dto.PaginatedData;
import com.alesharik.storemain.dto.ProductTypeDto;
import com.alesharik.storemain.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ProductTypeController {
    private final ProductTypeRepository productTypeRepository;

    @GetMapping("/admin/rest/producttypes")
    @ResponseBody
    public PaginatedData<ProductTypeDto> getProducts(
            @RequestParam("draw") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "20") int length
    ) {
        var data = productTypeRepository.findAll(PageRequest.of(start, length));
        return new PaginatedData<>(draw, data, ProductTypeDto::new);
    }

    @RequestMapping("/admin/producttypes")
    public String products() {
        return "admin_producttypes";
    }
}
