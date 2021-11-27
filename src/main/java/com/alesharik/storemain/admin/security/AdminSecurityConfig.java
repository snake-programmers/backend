package com.alesharik.storemain.admin.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/admin/**")
                .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login/admin")
                .loginProcessingUrl("/login/admin_proc")
                .failureUrl("/login/admin?error=true")
                .defaultSuccessUrl("/admin")
                .and()
                .logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .csrf().disable();
    }
}
