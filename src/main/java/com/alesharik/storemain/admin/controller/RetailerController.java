package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.dto.PaginatedData;
import com.alesharik.storemain.dto.RetailerDto;
import com.alesharik.storemain.entity.Retailer;
import com.alesharik.storemain.repository.RetailerRepository;
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
public class RetailerController {
    private final RetailerRepository retailerRepository;

    @GetMapping("/admin/rest/retailers")
    @ResponseBody
    public PaginatedData<RetailerDto> getRetailers(
            @RequestParam("draw") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "20") int length
    ) {
        var data = retailerRepository.findAll(PageRequest.of(start, length));
        return new PaginatedData<>(draw, data, RetailerDto::new);
    }

    @GetMapping("/admin/rest/retailers/suggest")
    @ResponseBody
    public List<RetailerDto> suggestRetailers(@RequestParam("input") String input) {
        var data = retailerRepository.findByNameContaining(input);
        return data.stream().map(RetailerDto::new).collect(Collectors.toList());
    }

    @RequestMapping("/admin/retailers")
    public String retailers() {
        return "admin_retailers";
    }

    @GetMapping("/admin/retailers/new")
    public ModelAndView newRetailer() {
        return new ModelAndView("admin_retailers_form", Map.of(
                "post_url", "/admin/retailers/new",
                "name", "",
                "logo", ""
        ));
    }

    @PostMapping("/admin/retailers/new")
    public ModelAndView postNewRetailer(@RequestParam("name") String name, @RequestParam("logo") String logo) {
        var entity = new Retailer();
        entity.setName(name);
        entity.setLogo(logo);
        retailerRepository.save(entity);
        return new ModelAndView("redirect:/admin/retailers");
    }

    @GetMapping("/admin/retailers/edit/{id}")
    public ModelAndView editRetailer(@PathVariable("id") Long id) {
        var entity = retailerRepository.findById(id).orElseThrow();
        return new ModelAndView("admin_retailers_form", Map.of(
                "post_url", "/admin/retailers/edit/" + entity.getId(),
                "name", entity.getName(),
                "logo", entity.getLogo()
        ));
    }

    @PostMapping("/admin/retailers/edit/{id}")
    public ModelAndView postNewRetailer(@PathVariable("id") Long id, @RequestParam("name") String name, @RequestParam("logo") String logo) {
        var entity = retailerRepository.findById(id).orElseThrow();
        entity.setName(name);
        entity.setLogo(logo);
        retailerRepository.save(entity);
        return new ModelAndView("redirect:/admin/retailers");
    }

    @GetMapping("/admin/retailers/delete/{id}")
    public ModelAndView deleteRetailer(@PathVariable("id") Long id) {
        retailerRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/retailers");
    }
}
