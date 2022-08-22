package com.wealthpilot.service;

import com.wealthpilot.constant.GameMessages;
import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Match;
import com.wealthpilot.entity.Team;
import com.wealthpilot.exception.GameScheduleException;
import com.wealthpilot.strategy.MultipleMatchStrategy;
import com.wealthpilot.strategy.SingleMatchStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.wealthpilot.constant.GameMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GamePlannerServiceTest {


    @InjectMocks
    GamePlannerService gamePlannerService;

    @Mock
    private SingleMatchStrategy singleMatchStrategy;

    @Mock
    private MultipleMatchStrategy multipleMatchStrategy;

    @Test
    public void testSingleMatchPlanSuccess() throws GameScheduleException {

        League leagueRequest =  League.builder()
                .league("My League")
                .country("Germany")
                .teams(Arrays.asList(
                        Team.builder().name("A").build(),
                        Team.builder().name("B").build()))
                .build();

        List<Match> matchesResponse = Arrays.asList(Match.builder()
                .date("2022-08-13")
                .firstOpponent("team2")
                .secondOpponent("team3")
                .build());

        when(singleMatchStrategy.generateMatches(leagueRequest)).thenReturn(matchesResponse);
        List<Match> actualResponse = gamePlannerService.createPlan("single", leagueRequest);
        assertNotNull(actualResponse);
    }

    @Test
    public void testMultiplePlanSuccess() throws GameScheduleException {

        League leagueRequest =  League.builder()
                .league("My League")
                .country("Germany")
                .teams(Arrays.asList(
                        Team.builder().name("A").build(),
                        Team.builder().name("B").build()))
                .build();

        List<Match> matchesResponse = Arrays.asList(Match.builder()
                .date("2022-08-13")
                .firstOpponent("team2")
                .secondOpponent("team3")
                .build());

        when(multipleMatchStrategy.generateMatches(leagueRequest)).thenReturn(matchesResponse);
        List<Match> actualResponse = gamePlannerService.createPlan("multiple", leagueRequest);
        assertNotNull(actualResponse);

    }

    @Test
    public void testInvalidTypeFailure() throws GameScheduleException {

        League leagueRequest =  League.builder()
                .league("My League")
                .country("Germany")
                .teams(Arrays.asList(
                        Team.builder().name("A").build(),
                        Team.builder().name("B").build()))
                .build();

        Throwable exception = assertThrows(GameScheduleException.class, () ->  gamePlannerService.createPlan("invalidType", leagueRequest));
        assertEquals("Invalid type", exception.getMessage());

    }


}
