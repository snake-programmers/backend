package com.alesharik.storemain.address.here;

import com.alesharik.storemain.address.AddressService;
import com.alesharik.storemain.dto.AddressDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HereAddressService implements AddressService {
    private final HereProperties properties;
    private final RestTemplate restTemplate;
    private final JsonMapper jsonMapper;

    @Override
    public List<AddressDto> suggest(String input) {
        if (input.isBlank()) return Collections.emptyList();
        var headers = new HttpHeaders();
//        headers.set("Authorization", "ApiKey " + properties.getApiKey());
        var entity = new HttpEntity<String>(headers);
        var response = restTemplate.exchange(
                UriComponentsBuilder.fromHttpUrl("https://geocode.search.hereapi.com/v1/geocode")
                        .queryParam("q", "{q}")
                        .queryParam("apiKey", properties.getApiKey())
                        .encode()
                        .buildAndExpand(input)
                        .toUri(),
                HttpMethod.GET,
                entity,
                String.class
        );
        JsonNode node;
        try {
            node = jsonMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
        var items = node.get("items");
        List<AddressDto> ret = new ArrayList<>(items.size());
        for (int i = 0; i < items.size(); i++) {
            var item = items.get(i);
            ret.add(new AddressDto(
                    item.get("address").get("label").asText(),
                    item.get("position").get("lat").asDouble(),
                    item.get("position").get("lng").asDouble()
            ));
        }
        return ret;
    }
}
