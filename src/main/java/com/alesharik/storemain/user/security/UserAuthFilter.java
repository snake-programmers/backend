package com.alesharik.storemain.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
public class UserAuthFilter extends GenericFilterBean {
    private final UserAuthenticationManager userAuthenticationManager;
    private final AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher("/user/**");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (antPathMatcher.matches((HttpServletRequest) request)) {
            var auth = ((HttpServletRequest) request).getHeader("Authorization");
            if (auth == null) throw new AuthenticationCredentialsNotFoundException("");
            if (!auth.startsWith("Basic ")) throw new AuthenticationCredentialsNotFoundException("");
            var base64 = auth.substring("Basic ".length());
            var creds = new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8);
            var loginAndPass = creds.split(":", 2);
            if (loginAndPass.length != 2) throw new AuthenticationCredentialsNotFoundException("");

            var authentication = userAuthenticationManager.authenticate(loginAndPass[0], loginAndPass[1]);
            var ctx = SecurityContextHolder.createEmptyContext();
            ctx.setAuthentication(authentication);
            SecurityContextHolder.setContext(ctx);
        }
        chain.doFilter(request, response);
    }
}
