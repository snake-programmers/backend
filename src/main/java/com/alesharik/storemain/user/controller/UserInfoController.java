package com.alesharik.storemain.user.controller;

import com.alesharik.storemain.dto.UserDto;
import com.alesharik.storemain.user.security.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoController {
    @GetMapping("/user/info")
    public UserDto getUserInfo() {
        var user = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUser();
        return new UserDto(user);
    }
}
