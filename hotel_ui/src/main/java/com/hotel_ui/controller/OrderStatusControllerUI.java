package com.hotel_ui.controller;

import com.hotel_ui.message.Messages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RequestMapping("/admin/orderStatuses")
@Controller
@AllArgsConstructor
public class OrderStatusControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_ORDER_STATUSES = URL_HOST + Messages.getMessage("ui.url.orderStatuses");

    @GetMapping()
    private String getAllOrderStatuses(Model model) {
        model.addAttribute("orderStatuses", restTemplate.getForObject(URL_ORDER_STATUSES, List.class));
        return "admin/orderStatuses";
    }
}
