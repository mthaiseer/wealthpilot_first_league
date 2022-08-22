package com.wealthpilot.algorithm;

import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Match;
import com.wealthpilot.exception.GameScheduleException;

import java.util.List;

public interface GameSchedulerAlgorithm {

    public void  createGamePlans(League league, boolean isSingleMatchPerDay) throws GameScheduleException;
    public List<Match> getFinalSchedule();
}
