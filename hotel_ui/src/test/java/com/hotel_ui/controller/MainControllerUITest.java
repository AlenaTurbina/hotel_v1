package com.hotel_ui.controller;

import com.hotel_ui.configuration.TestConfigurationUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = TestConfigurationUserDetails.class)
@AutoConfigureMockMvc
class MainControllerUITest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testHome() throws Exception {
        mockMvc.perform(get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("home/first"))
                .andDo(print());
    }

    @Test
    void testRoomPhotos() throws Exception {
        mockMvc.perform(get("/home/roomsInfo")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("home/roomsInfo"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testAdminPage() throws Exception {
        mockMvc.perform(get("/admin")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/adminPage"))
                .andDo(print());
    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(get("/login")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("account/login"))
                .andDo(print());
    }
}