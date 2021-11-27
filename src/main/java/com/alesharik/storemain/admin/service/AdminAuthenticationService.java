package com.alesharik.storemain.admin.service;

import com.alesharik.storemain.admin.security.OperatorAuthentication;
import com.alesharik.storemain.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthenticationService {
    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;

    public Authentication authenticate(String login, String password) {
        var operator = operatorRepository.findByLogin(login);
        if (operator == null) {
            throw new UsernameNotFoundException("Username " + login + " not found");
        }
        if (!passwordEncoder.matches(password, operator.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return new OperatorAuthentication(login);
    }
}
