package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.admin.service.AdminAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class AdminLoginController {
    private final AdminAuthenticationService adminAuthenticationService;

    @RequestMapping("/login/admin")
    public ModelAndView adminLogin(@RequestParam(value = "error", defaultValue = "false") boolean error) {
        return new ModelAndView("admin_login", Collections.singletonMap("error", error));
    }

    @RequestMapping(value = "/login/admin_proc", method = RequestMethod.POST)
    public ModelAndView adminLoginProcess(HttpServletRequest request, @RequestParam("login") String login, @RequestParam("password") String password) {
        try {
            var auth = adminAuthenticationService.authenticate(login, password);
            var ctx = SecurityContextHolder.getContext();
            ctx.setAuthentication(auth);
            request.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, ctx);
            return new ModelAndView("redirect:/admin/operators");
        } catch (AuthenticationException e) {
            return new ModelAndView("redirect:/login/admin?error=true");
        }
    }
}
