package com.alesharik.storemain.admin.controller;

import com.alesharik.storemain.dto.OperatorDto;
import com.alesharik.storemain.dto.PaginatedData;
import com.alesharik.storemain.entity.Operator;
import com.alesharik.storemain.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OperatorController {
    private final OperatorRepository operatorRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/admin/rest/operators")
    @ResponseBody
    public PaginatedData<OperatorDto> getOperators(
            @RequestParam("draw") int draw,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "length", defaultValue = "20") int length
    ) {
        var data = operatorRepository.findAllBy(PageRequest.of(start, length));
        return new PaginatedData<>(draw, data, operator -> new OperatorDto(operator.getId(), operator.getLogin()));
    }

    @RequestMapping("/admin/operators")
    public String operators() {
        return "admin_operators";
    }

    @GetMapping("/admin/operators/new")
    public ModelAndView newOperator() {
        return new ModelAndView("admin_operator_form", Map.of(
                "post_url", "/admin/operators/new",
                "login", "",
                "password", ""
        ));
    }

    @PostMapping("/admin/operators/new")
    public ModelAndView postNewOperator(@RequestParam("login") String login, @RequestParam("password") String password) {
        var entity = new Operator();
        entity.setLogin(login);
        entity.setPassword(passwordEncoder.encode(password));
        operatorRepository.save(entity);
        return new ModelAndView("redirect:/admin/operators");
    }

    @GetMapping("/admin/operators/edit/{id}")
    public ModelAndView editOperator(@PathVariable("id") Long id) {
        var entity = operatorRepository.findById(id).orElseThrow();
        return new ModelAndView("admin_operator_form", Map.of(
                "post_url", "/admin/operators/edit/" + entity.getId(),
                "login", entity.getLogin(),
                "password", ""
        ));
    }

    @PostMapping("/admin/operators/edit/{id}")
    public ModelAndView postNewOperator(@PathVariable("id") Long id, @RequestParam("login") String login, @RequestParam("password") String password) {
        var entity = operatorRepository.findById(id).orElseThrow();
        entity.setLogin(login);
        if (!password.isBlank()) entity.setPassword(passwordEncoder.encode(password));
        operatorRepository.save(entity);
        return new ModelAndView("redirect:/admin/operators");
    }

    @GetMapping("/admin/operators/delete/{id}")
    public ModelAndView deleteOperator(@PathVariable("id") Long id) {
        operatorRepository.deleteById(id);
        return new ModelAndView("redirect:/admin/operators");
    }
}
