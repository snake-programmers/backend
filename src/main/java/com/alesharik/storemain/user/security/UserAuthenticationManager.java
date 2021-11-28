package com.alesharik.storemain.user.security;

import com.alesharik.storemain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAuthentication authenticate(String login, String password) {
        var user = userRepository.findByEmail(login);
        if (user == null) throw new UsernameNotFoundException("User not found");
        if (!passwordEncoder.matches(password, user.getPassword())) throw new BadCredentialsException("Wrong password");
        return new UserAuthentication(user);
    }
}
