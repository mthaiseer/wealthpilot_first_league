package com.wealthpilot.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
public class HealthControllerTest {

    public static final String APPLICATION_IS_ALIVE = "application is alive";
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testPingSuccess() throws Exception {

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .get("/api/v1/league/ping")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result  = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(APPLICATION_IS_ALIVE))
                .andReturn();
        assertEquals(APPLICATION_IS_ALIVE,
                result.getResponse().getContentAsString());


    }
}
