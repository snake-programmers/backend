package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.dto.DeliveryPointDto;
import com.alesharik.storemain.dto.PaginatedData;
import com.alesharik.storemain.entity.DeliveryPoint;
import com.alesharik.storemain.repository.DeliveryPointRepository;
import com.alesharik.storemain.repository.DeliveryServiceRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DeliveryPointController {
    private final DeliveryServiceRepository deliveryServiceRepository;
    private final DeliveryPointRepository deliveryPointRepository;
    private final GeometryFactory geometryFactory;

    @GetMapping("/admin/rest/deliverypoints")
    @ResponseBody
    public PaginatedData<DeliveryPointDto> getDeliveryPoints(
            @RequestParam("draw") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "20") int length
    ) {
        var data = deliveryPointRepository.findAll(PageRequest.of(start, length));
        return new PaginatedData<>(draw, data, DeliveryPointDto::new);
    }

    @RequestMapping("/admin/deliverypoints")
    public String deliveryPoints() {
        return "admin_deliverypoints";
    }

    @GetMapping("/admin/deliverypoints/new")
    public ModelAndView newRetailer() {
        return new ModelAndView("admin_deliverypoints_form", Map.of(
                "post_url", "/admin/deliverypoints/new",
                "name", ""
        ));
    }

    @PostMapping("/admin/deliverypoints/new")
    public ModelAndView postNewRetailer(
            @RequestParam("name") String name,
            @RequestParam("lat") float lat,
            @RequestParam("lon") float lon,
            @RequestParam("address") String address,
            @RequestParam("deliveryservice") long deliveryservice
    ) {
        var entity = new DeliveryPoint();
        entity.setName(name);
        entity.setAddress(address);
        entity.setLocation(geometryFactory.createPoint(new CoordinateXY(lat, lon)));
        entity.setDeliveryService(deliveryServiceRepository.findById(deliveryservice).orElseThrow());
        deliveryPointRepository.save(entity);
        return new ModelAndView("redirect:/admin/deliverypoints");
    }

    @GetMapping("/admin/deliverypoints/edit/{id}")
    public ModelAndView editRetailer(@PathVariable("id") Long id) {
        var entity = deliveryPointRepository.findById(id).orElseThrow();
        return new ModelAndView("admin_deliverypoints_form", Map.of(
                "post_url", "/admin/deliverypoints/edit/" + entity.getId(),
                "name", entity.getName(),
                "lat", entity.getLocation().getX(),
                "lon", entity.getLocation().getY(),
                "address", entity.getAddress(),
                "deliveryservice_name", entity.getDeliveryService().getName(),
                "deliveryservice_id", entity.getDeliveryService().getId()
        ));
    }

    @PostMapping("/admin/deliverypoints/edit/{id}")
    public ModelAndView postNewRetailer(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("lat") float lat,
            @RequestParam("lon") float lon,
            @RequestParam("address") String address,
            @RequestParam("deliveryservice") long deliveryservice
    ) {
        var entity = deliveryPointRepository.findById(id).orElseThrow();
        entity.setName(name);
        entity.setAddress(address);
        entity.setLocation(geometryFactory.createPoint(new CoordinateXY(lat, lon)));
        entity.setDeliveryService(deliveryServiceRepository.findById(deliveryservice).orElseThrow());
        deliveryPointRepository.save(entity);
        return new ModelAndView("redirect:/admin/deliverypoints");
    }

    @GetMapping("/admin/deliverypoints/delete/{id}")
    public ModelAndView deleteRetailer(@PathVariable("id") Long id) {
        deliveryPointRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/deliverypoints");
    }
}
