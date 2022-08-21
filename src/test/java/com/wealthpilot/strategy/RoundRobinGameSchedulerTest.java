package com.wealthpilot.strategy;

import com.wealthpilot.entity.Match;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class RoundRobinGameSchedulerTest {


    @InjectMocks
    private RoundRobinGameScheduler roundRobinGameScheduler;

    @BeforeAll
    public void  init(){
        System.out.println("init called");
    }

    @Test
    public void testCreateSchedules(){

        List<Match> matches = roundRobinGameScheduler.createGamePlans();
        assertNotNull(matches);



    }


}
