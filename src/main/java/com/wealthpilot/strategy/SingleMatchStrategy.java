package com.wealthpilot.strategy;

import com.wealthpilot.algorithm.RoundRobinGameSchedulerAlgorithm;
import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Match;
import com.wealthpilot.exception.GameScheduleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingleMatchStrategy {

    @Autowired
    private RoundRobinGameSchedulerAlgorithm gameSchedulerAlgorithm;

    public  List<Match> generateMatches(League league) throws GameScheduleException {
        gameSchedulerAlgorithm.createGamePlans(league, true);
        return gameSchedulerAlgorithm.getFinalSchedule();
    }
}
