package com.alesharik.storemain.dto;

import com.alesharik.storemain.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private final long id;
    private final String email;
    private final String name;
    private final String homeAddress;
    private final float lat;
    private final float lon;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.homeAddress = user.getHomeAddress();
        this.lat = (float) user.getHomeLocation().getX();
        this.lon = (float) user.getHomeLocation().getY();
    }
}
