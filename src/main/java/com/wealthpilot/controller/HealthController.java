package com.wealthpilot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/league")
public class HealthController {

    @GetMapping("/ping")
    public String ping(){
        return "application is alive";
    }


}
