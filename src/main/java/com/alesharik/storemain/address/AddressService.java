package com.alesharik.storemain.address;

import com.alesharik.storemain.dto.AddressDto;

import java.util.List;

public interface AddressService {
    List<AddressDto> suggest(String input);
}
