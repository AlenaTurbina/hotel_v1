package com.hotel_ui.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import static com.hotel_ui.security.UserPrincipal.getPrincipal;


@Controller
@AllArgsConstructor
public class MainControllerUI {

    private RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "home/first";
    }

    @GetMapping(value = "/home/roomsInfo")
    public String roomPhotos() {
        return "home/roomsInfo";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("user", getPrincipal());
        return "admin/adminPage";
    }

    @GetMapping("/login")
    public String loginSimple() {
        return "account/login";
    }

}