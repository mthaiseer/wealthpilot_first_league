package com.wealthpilot.controller;

import com.wealthpilot.dto.GamePlannerRequest;
import com.wealthpilot.entity.Match;
import com.wealthpilot.exception.GameScheduleException;
import com.wealthpilot.service.GamePlannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/league")
public class GamePlannerController {

    Logger logger = LoggerFactory.getLogger(GamePlannerController.class);

    @Autowired
    GamePlannerService gamePlannerService;

    @PostMapping("/plan/{type}")
    public List<Match>  createSingleGamePlan(@PathVariable String type, @RequestBody GamePlannerRequest request){

        logger.info("parameters are {} , {}", type, request);
        try {
            return gamePlannerService.createPlan(type, request.to());
        } catch (GameScheduleException e) {
            e.printStackTrace();
        }
        return null;
    }

}
