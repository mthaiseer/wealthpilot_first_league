package com.wealthpilot.controller;

import com.wealthpilot.constant.GameMessages;
import com.wealthpilot.dto.GamePlannerRequest;
import com.wealthpilot.entity.Match;
import com.wealthpilot.exception.GameScheduleException;
import com.wealthpilot.service.GamePlannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author Mohamed Thaiseer
 * @Description Controller class accept game request and pass to service for further process
 */
@RestController
@RequestMapping("/api/v1/league")
public class GamePlannerController {

    Logger logger = LoggerFactory.getLogger(GamePlannerController.class);

    @Autowired
    GamePlannerService gamePlannerService;

    @PostMapping("/plan/{type}")
    @ResponseBody
    public ResponseEntity<List<Match>>  createSingleGamePlan(@PathVariable String type,
                                                             @Valid @RequestBody GamePlannerRequest request) throws GameScheduleException {

        logger.info("parameters are type  {} , request {}", type, request);
        try {

            if(request == null){
                throw new GameScheduleException(GameMessages.BAD_REQUEST);
            }
            
            List<Match> plans = gamePlannerService.createPlan(type, request.to());
            logger.debug("Response received {}", plans);
            return ResponseEntity.status(HttpStatus.CREATED).body(plans);
        } catch (GameScheduleException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new GameScheduleException(e.getMessage());
        }
    }

}
