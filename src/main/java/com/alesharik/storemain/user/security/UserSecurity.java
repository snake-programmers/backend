package com.alesharik.storemain.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class UserSecurity extends WebSecurityConfigurerAdapter {
    private final UserAuthenticationManager userAuthenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new UserAuthFilter(userAuthenticationManager), BasicAuthenticationFilter.class);
        http.authorizeRequests()
                .antMatchers("/userregister")
                .permitAll()
                .and()
                .csrf().disable();
    }
}
