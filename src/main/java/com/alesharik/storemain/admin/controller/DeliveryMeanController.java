package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.dto.DeliveryMeanDto;
import com.alesharik.storemain.dto.PaginatedData;
import com.alesharik.storemain.entity.DeliveryMean;
import com.alesharik.storemain.repository.DeliveryMeanRepository;
import com.alesharik.storemain.repository.DeliveryServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DeliveryMeanController {
    private final DeliveryServiceRepository deliveryServiceRepository;
    private final DeliveryMeanRepository deliveryMeanRepository;

    @GetMapping("/admin/rest/deliverymeans")
    @ResponseBody
    public PaginatedData<DeliveryMeanDto> getDeliveryMeans(
            @RequestParam("draw") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "20") int length
    ) {
        var data = deliveryMeanRepository.findAll(PageRequest.of(start, length));
        return new PaginatedData<>(draw, data, DeliveryMeanDto::new);
    }

    @RequestMapping("/admin/deliverymeans")
    public String deliveryMeans() {
        return "admin_deliverymeans";
    }

    @GetMapping("/admin/deliverymeans/new")
    public ModelAndView newDeliveryMean() {
        return new ModelAndView("admin_deliverymeans_form", Map.of(
                "post_url", "/admin/deliverymeans/new",
                "name", ""
        ));
    }

    @PostMapping("/admin/deliverymeans/new")
    public ModelAndView postNewDeliveryMean(
            @RequestParam("name") String name,
            @RequestParam("minMeters") int minMeters,
            @RequestParam("maxMeters") int maxMeters,
            @RequestParam("pricePerKm") int pricePerKm,
            @RequestParam("deliveryservice") long deliveryservice
    ) {
        var entity = new DeliveryMean();
        entity.setName(name);
        entity.setMinMeters(minMeters);
        entity.setMaxMeters(maxMeters);
        entity.setPricePerKm(pricePerKm);
        entity.setDeliveryService(deliveryServiceRepository.findById(deliveryservice).orElseThrow());
        deliveryMeanRepository.save(entity);
        return new ModelAndView("redirect:/admin/deliverymeans");
    }

    @GetMapping("/admin/deliverymeans/edit/{id}")
    public ModelAndView editDeliveryMean(@PathVariable("id") Long id) {
        var entity = deliveryMeanRepository.findById(id).orElseThrow();
        return new ModelAndView("admin_deliverymeans_form", Map.of(
                "post_url", "/admin/deliverymeans/edit/" + entity.getId(),
                "name", entity.getName(),
                "maxMeters", entity.getMaxMeters(),
                "minMeters", entity.getMinMeters(),
                "pricePerKm", entity.getPricePerKm(),
                "deliveryservice_name", entity.getDeliveryService().getName(),
                "deliveryservice_id", entity.getDeliveryService().getId()
        ));
    }

    @PostMapping("/admin/deliverymeans/edit/{id}")
    public ModelAndView updateDeliveryMean(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("minMeters") int minMeters,
            @RequestParam("maxMeters") int maxMeters,
            @RequestParam("pricePerKm") int pricePerKm,
            @RequestParam("deliveryservice") long deliveryservice
    ) {
        var entity = deliveryMeanRepository.findById(id).orElseThrow();
        entity.setName(name);
        entity.setMinMeters(minMeters);
        entity.setMaxMeters(maxMeters);
        entity.setPricePerKm(pricePerKm);
        entity.setDeliveryService(deliveryServiceRepository.findById(deliveryservice).orElseThrow());
        deliveryMeanRepository.save(entity);
        return new ModelAndView("redirect:/admin/deliverymeans");
    }

    @GetMapping("/admin/deliverymeans/delete/{id}")
    public ModelAndView deleteDeliveryMean(@PathVariable("id") Long id) {
        deliveryMeanRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/deliverymeans");
    }
}
