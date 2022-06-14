package com.hotel_ui.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
