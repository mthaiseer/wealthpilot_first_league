package com.wealthpilot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wealthpilot.dto.GamePlannerRequest;
import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Match;
import com.wealthpilot.entity.Team;
import com.wealthpilot.service.GamePlannerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static com.wealthpilot.constant.GameMessages.TYPE_SINGLE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(GamePlannerController.class)
@ExtendWith(MockitoExtension.class)
public class GamePlannerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GamePlannerService gamePlannerService;

    @Test
    public void testGamePlannerController_singleMatchPerSaturday() throws Exception {


        League leagueRequest =  League.builder()
                .league("My League")
                .country("Germany")
                .teams(Arrays.asList(
                        Team.builder().name("A").build())).build();


        when(gamePlannerService.createPlan(TYPE_SINGLE,leagueRequest ))
                .thenReturn(
                Arrays.asList(Match.builder()
                        .date("2022-08-13")
                        .firstOpponent("team2")
                        .secondOpponent("team3")
                        .build())
        );

        MockHttpServletRequestBuilder request  = MockMvcRequestBuilders
                .post("/api/v1/league/plan/single" )
                .content(asJsonString(GamePlannerRequest.builder()
                        .league("testLeague")
                        .country("Germany")
                        .teams(Arrays.asList(
                        Team.builder().name("A").build())).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
