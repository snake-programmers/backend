package com.alesharik.storemain.controller;

import com.alesharik.storemain.address.AddressService;
import com.alesharik.storemain.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/address/suggest")
    public List<AddressDto> suggestAddresses(@RequestParam("input") String input) {
        return addressService.suggest(input);
    }
}
