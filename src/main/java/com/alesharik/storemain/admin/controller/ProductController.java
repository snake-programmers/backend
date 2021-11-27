package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.admin.service.ProductTypeService;
import com.alesharik.storemain.dto.PaginatedData;
import com.alesharik.storemain.dto.ProductDto;
import com.alesharik.storemain.entity.Product;
import com.alesharik.storemain.repository.ProductRepository;
import com.alesharik.storemain.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final ProductTypeService productTypeService;

    @GetMapping("/admin/rest/products")
    @ResponseBody
    public PaginatedData<ProductDto> getProducts(
            @RequestParam("draw") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "20") int length
    ) {
        var data = productRepository.findAll(PageRequest.of(start, length));
        return new PaginatedData<>(draw, data, ProductDto::new);
    }

    @RequestMapping("/admin/products")
    public String products() {
        return "admin_products";
    }

    @GetMapping("/admin/products/new")
    public ModelAndView newProduct() {
        return new ModelAndView("admin_products_form", Map.of(
                "post_url", "/admin/products/new",
                "name", "",
                "picture", "",
                "volume", "",
                "humanVolume", "",
                "price", 0,
                "itemsPerPack", 0,
                "store_name", "",
                "store_id", ""
        ));
    }

    @PostMapping("/admin/products/new")
    public ModelAndView postNewProduct(
            @RequestParam("name") String name,
            @RequestParam("picture") String picture,
            @RequestParam("volume") String volume,
            @RequestParam("humanVolume") String humanVolume,
            @RequestParam("price") int price,
            @RequestParam("itemsPerPack") int itemsPerPack,
            @RequestParam("store") long store
    ) {
        var entity = new Product();
        entity.setName(name);
        entity.setPicture(picture);
        entity.setVolume(volume);
        entity.setHumanVolume(humanVolume);
        entity.setPrice(price);
        entity.setItemsPerPack(itemsPerPack);
        entity.setStore(storeRepository.findById(store).orElseThrow());
        entity.setProductType(productTypeService.getProductType(entity));
        productRepository.save(entity);
        return new ModelAndView("redirect:/admin/products");
    }

    @GetMapping("/admin/products/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long id) {
        var entity = productRepository.findById(id).orElseThrow();
        return new ModelAndView("admin_products_form", Map.of(
                "post_url", "/admin/products/edit/" + entity.getId(),
                "name", entity.getName(),
                "picture", entity.getPicture(),
                "volume", entity.getVolume(),
                "humanVolume", entity.getHumanVolume(),
                "price", entity.getPrice(),
                "itemsPerPack", entity.getItemsPerPack(),
                "store_name", entity.getStore().getName(),
                "store_id", entity.getStore().getId()
        ));
    }

    @PostMapping("/admin/products/edit/{id}")
    public ModelAndView postEditProduct(
            @PathVariable("id") Long id,
            @RequestParam("volume") String volume,
            @RequestParam("picture") String picture,
            @RequestParam("humanVolume") String humanVolume,
            @RequestParam("price") int price,
            @RequestParam("itemsPerPack") int itemsPerPack,
            @RequestParam("store") long store
    ) {
        var entity = productRepository.findById(id).orElseThrow();
        entity.setVolume(volume);
        entity.setPicture(picture);
        entity.setHumanVolume(humanVolume);
        entity.setPrice(price);
        entity.setItemsPerPack(itemsPerPack);
        entity.setStore(storeRepository.findById(store).orElseThrow());
        productRepository.save(entity);
        return new ModelAndView("redirect:/admin/products");
    }

    @GetMapping("/admin/products/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/products");
    }
}
