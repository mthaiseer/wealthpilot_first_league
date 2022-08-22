package com.wealthpilot.service;

import static com.wealthpilot.constant.GameMessages.*;

import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Match;
import com.wealthpilot.exception.GameScheduleException;
import com.wealthpilot.strategy.MultipleMatchStrategy;
import com.wealthpilot.strategy.SingleMatchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamePlannerService {


    @Autowired
    private SingleMatchStrategy singleMatchStrategy;

    @Autowired
    private MultipleMatchStrategy multipleMatchStrategy;


    /**
     *
     * @param type
     * @param league
     * @return
     * @throws GameScheduleException
     */
    public List<Match> createPlan(String type, League league) throws GameScheduleException {

        if(!isValidType(type)){
            throw new GameScheduleException(INVALID_GAME_TYPE);
        }

        switch (type){
            case TYPE_SINGLE:
                return singleMatchStrategy.generateMatches(league);
            case TYPE_MULTIPLE:
                return  multipleMatchStrategy.generateMatches(league);
            default:
                throw new GameScheduleException(INVALID_GAME_TYPE);
        }
    }

    /**
     *
     * @param type
     * @return
     */
    public boolean isValidType(String type) {
        return type != null && (type.equalsIgnoreCase(TYPE_SINGLE) || type.equalsIgnoreCase(TYPE_MULTIPLE));
    }


}
