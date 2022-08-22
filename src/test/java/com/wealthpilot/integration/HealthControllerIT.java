package com.wealthpilot.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HealthControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads(){
        String response  =  this.restTemplate.getForObject("/api/v1/league/ping", String.class);
        assertEquals("application is alive", response);
    }
}
