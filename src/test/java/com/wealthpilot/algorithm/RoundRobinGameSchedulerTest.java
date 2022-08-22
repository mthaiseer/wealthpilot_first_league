package com.wealthpilot.algorithm;

import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Match;
import com.wealthpilot.entity.Team;

import com.wealthpilot.exception.GameScheduleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoundRobinGameSchedulerTest {


    private RoundRobinGameSchedulerAlgorithm roundRobinGameScheduler;
    Logger logger = LoggerFactory.getLogger(RoundRobinGameSchedulerTest.class);


    League league = null;

    @BeforeEach
    public void init() {

        List<Team> teamList = new ArrayList<>();
        teamList.add(Team.builder().name("A").build());
        teamList.add(Team.builder().name("B").build());
        teamList.add(Team.builder().name("C").build());
       // teamList.add(Team.builder().name("D").build());

        league =  League.builder()
                .league("My League")
                .country("Germany")
                .teams(teamList).build();
    }

    @Test
    public void testCreateSchedules() throws GameScheduleException {

        roundRobinGameScheduler = new RoundRobinGameSchedulerAlgorithm();
        roundRobinGameScheduler.createGamePlans(league, true);
        List<Match> schedules = roundRobinGameScheduler.getFinalSchedule();
        logger.debug(schedules.toString());
        assertNotNull(schedules);
        Match firstMatch = schedules.get(0);
        assertNotNull(firstMatch);

        assertEquals("B", schedules.get(0).getFirstOpponent());
        assertEquals("C", schedules.get(0).getSecondOpponent());

        assertEquals("A", schedules.get(1).getFirstOpponent());
        assertEquals("C", schedules.get(1).getSecondOpponent());

        assertEquals("A", schedules.get(2).getFirstOpponent());
        assertEquals("B", schedules.get(2).getSecondOpponent());

        assertEquals(6, schedules.size());


    }

    @Test
    public void testCreateSchedulesError() throws GameScheduleException {
        roundRobinGameScheduler = new RoundRobinGameSchedulerAlgorithm();
        Throwable exception = assertThrows(GameScheduleException.class, () ->  roundRobinGameScheduler.createGamePlans(new League(), true));
        assertEquals("Invalid request received", exception.getMessage());
    }


}
