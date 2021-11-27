package com.alesharik.storemain.address.here;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("here")
public class HereProperties {
    private String apiKey;
}
