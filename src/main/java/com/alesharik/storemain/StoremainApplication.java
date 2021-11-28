package com.alesharik.storemain;

import com.alesharik.storemain.entity.Operator;
import com.alesharik.storemain.repository.OperatorRepository;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StoremainApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(StoremainApplication.class, args);
        var repo = ctx.getBean(OperatorRepository.class);
        if (repo.count() == 0) {
            var entity = new Operator();
            entity.setLogin("admin");
            entity.setPassword(ctx.getBean(PasswordEncoder.class).encode("admin"));
            repo.save(entity);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JsonMapper jsonMapper() {
        return new JsonMapper();
    }

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory();
    }

    @Bean
    public HttpFirewall httpFirewall() {
        var firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        return firewall;
    }
}
