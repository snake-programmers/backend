package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.dto.DeliveryServiceDto;
import com.alesharik.storemain.dto.PaginatedData;
import com.alesharik.storemain.entity.DeliveryService;
import com.alesharik.storemain.repository.DeliveryServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DeliveryServiceController {
    private final DeliveryServiceRepository deliveryServiceRepository;

    @GetMapping("/admin/rest/deliveryservices")
    @ResponseBody
    public PaginatedData<DeliveryServiceDto> getDeliveryServices(
            @RequestParam("draw") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "20") int length
    ) {
        var data = deliveryServiceRepository.findAll(PageRequest.of(start, length));
        return new PaginatedData<>(draw, data, DeliveryServiceDto::new);
    }

    @GetMapping("/admin/rest/deliveryservices/suggest")
    @ResponseBody
    public List<DeliveryServiceDto> suggestDeliveryServices(@RequestParam("input") String input) {
        var data = deliveryServiceRepository.findByNameContaining(input);
        return data.stream().map(DeliveryServiceDto::new).collect(Collectors.toList());
    }

    @RequestMapping("/admin/deliveryservices")
    public String deliveryServices() {
        return "admin_deliveryservices";
    }

    @GetMapping("/admin/deliveryservices/new")
    public ModelAndView newDeliveryService() {
        return new ModelAndView("admin_deliveryservices_form", Map.of(
                "post_url", "/admin/deliveryservices/new",
                "name", "",
                "orderReceiveEmail", "",
                "orderReceiveLink", ""
        ));
    }

    @PostMapping("/admin/deliveryservices/new")
    public ModelAndView postNewDeliveryService(@RequestParam("name") String name, @RequestParam("orderReceiveEmail") String orderReceiveEmail, @RequestParam("orderReceiveLink") String orderReceiveLink) {
        var entity = new DeliveryService();
        entity.setName(name);
        entity.setOrderReceiveEmail(orderReceiveEmail);
        entity.setOrderReceiveLink(orderReceiveLink);
        deliveryServiceRepository.save(entity);
        return new ModelAndView("redirect:/admin/deliveryservices");
    }

    @GetMapping("/admin/deliveryservices/edit/{id}")
    public ModelAndView editDeliveryService(@PathVariable("id") Long id) {
        var entity = deliveryServiceRepository.findById(id).orElseThrow();
        return new ModelAndView("admin_deliveryservices_form", Map.of(
                "post_url", "/admin/deliveryservices/edit/" + entity.getId(),
                "name", entity.getName(),
                "orderReceiveEmail", entity.getOrderReceiveEmail(),
                "orderReceiveLink", entity.getOrderReceiveLink()
        ));
    }

    @PostMapping("/admin/deliveryservices/edit/{id}")
    public ModelAndView postNewDeliveryService(@PathVariable("id") Long id, @RequestParam("name") String name, @RequestParam("orderReceiveEmail") String orderReceiveEmail, @RequestParam("orderReceiveLink") String orderReceiveLink) {
        var entity = deliveryServiceRepository.findById(id).orElseThrow();
        entity.setName(name);
        entity.setOrderReceiveLink(orderReceiveLink);
        entity.setOrderReceiveEmail(orderReceiveEmail);
        deliveryServiceRepository.save(entity);
        return new ModelAndView("redirect:/admin/deliveryservices");
    }

    @GetMapping("/admin/deliveryservices/delete/{id}")
    public ModelAndView deleteDeliveryService(@PathVariable("id") Long id) {
        deliveryServiceRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/deliveryservices");
    }
}
