package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.dto.PaginatedData;
import com.alesharik.storemain.dto.StoreDto;
import com.alesharik.storemain.entity.Store;
import com.alesharik.storemain.repository.RetailerRepository;
import com.alesharik.storemain.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StoreController {
    private final RetailerRepository retailerRepository;
    private final StoreRepository storeRepository;
    private final GeometryFactory geometryFactory;

    @GetMapping("/admin/rest/stores")
    @ResponseBody
    public PaginatedData<StoreDto> getRetailers(
            @RequestParam("draw") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "20") int length
    ) {
        var data = storeRepository.findAll(PageRequest.of(start, length));
        return new PaginatedData<>(draw, data, StoreDto::new);
    }

    @GetMapping("/admin/rest/stores/suggest")
    @ResponseBody
    public List<StoreDto> suggestStores(@RequestParam("input") String input) {
        var data = storeRepository.findByNameContaining(input);
        return data.stream().map(StoreDto::new).collect(Collectors.toList());
    }

    @RequestMapping("/admin/stores")
    public String retailers() {
        return "admin_stores";
    }

    @GetMapping("/admin/stores/new")
    public ModelAndView newRetailer() {
        return new ModelAndView("admin_stores_form", Map.of(
                "post_url", "/admin/stores/new",
                "name", "",
                "logo", ""
        ));
    }

    @PostMapping("/admin/stores/new")
    public ModelAndView postNewRetailer(
            @RequestParam("name") String name,
            @RequestParam("lat") float lat,
            @RequestParam("lon") float lon,
            @RequestParam("address") String address,
            @RequestParam("retailer") long retailer
    ) {
        var entity = new Store();
        entity.setName(name);
        entity.setAddress(address);
        entity.setLocation(geometryFactory.createPoint(new CoordinateXY(lat, lon)));
        entity.setRetailer(retailerRepository.findById(retailer).orElseThrow());
        storeRepository.save(entity);
        return new ModelAndView("redirect:/admin/stores");
    }

    @GetMapping("/admin/stores/edit/{id}")
    public ModelAndView editRetailer(@PathVariable("id") Long id) {
        var entity = storeRepository.findById(id).orElseThrow();
        return new ModelAndView("admin_stores_form", Map.of(
                "post_url", "/admin/stores/edit/" + entity.getId(),
                "name", entity.getName(),
                "lat", entity.getLocation().getX(),
                "lon", entity.getLocation().getY(),
                "address", entity.getAddress(),
                "retailer_name", entity.getRetailer().getName(),
                "retailer_id", entity.getRetailer().getId()
        ));
    }

    @PostMapping("/admin/stores/edit/{id}")
    public ModelAndView postNewRetailer(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("lat") float lat,
            @RequestParam("lon") float lon,
            @RequestParam("address") String address,
            @RequestParam("retailer") long retailer
    ) {
        var entity = storeRepository.findById(id).orElseThrow();
        entity.setName(name);
        entity.setAddress(address);
        entity.setLocation(geometryFactory.createPoint(new CoordinateXY(lat, lon)));
        entity.setRetailer(retailerRepository.findById(retailer).orElseThrow());
        storeRepository.save(entity);
        return new ModelAndView("redirect:/admin/stores");
    }

    @GetMapping("/admin/stores/delete/{id}")
    public ModelAndView deleteRetailer(@PathVariable("id") Long id) {
        storeRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/stores");
    }
}
