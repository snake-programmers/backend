package com.alesharik.storemain.user.controller;

import com.alesharik.storemain.entity.User;
import com.alesharik.storemain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserRepository userRepository;
    private final GeometryFactory geometryFactory;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/userregister")
    public void register(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon
    ) {
        var user = new User();
        user.setEmail(email);
        user.setHomeAddress(address);
        user.setHomeLocation(geometryFactory.createPoint(new CoordinateXY(lat, lon)));
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
