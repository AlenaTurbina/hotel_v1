package com.hotel_ui.security;

import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal {

    public static String getPrincipal() {
        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
