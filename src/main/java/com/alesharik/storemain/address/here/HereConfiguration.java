package com.alesharik.storemain.address.here;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HereProperties.class)
public class HereConfiguration {
}
