package com.hotel_ui.controller;

import com.hotel_ui.message.Messages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RequestMapping("/admin/roles")
@Controller
@AllArgsConstructor
public class RoleControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_ROLES = URL_HOST + Messages.getMessage("ui.url.roles");

    @GetMapping()
    private String getAllRoles(Model model) {
        model.addAttribute("roles", restTemplate.getForObject(URL_ROLES, List.class));
        return "admin/roles";
    }
}
