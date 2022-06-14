package com.hotel_ui.controller;

import com.hotel_ui.message.Messages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RequestMapping("/admin/userStatuses")
@Controller
@AllArgsConstructor
public class UserStatusControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_USER_STATUSES = URL_HOST + Messages.getMessage("ui.url.userStatuses");

    @GetMapping()
    private String getAllUserStatuses(Model model) {
        model.addAttribute("userStatuses", restTemplate.getForObject(URL_USER_STATUSES, List.class));
        return "admin/userStatuses";
    }

}
