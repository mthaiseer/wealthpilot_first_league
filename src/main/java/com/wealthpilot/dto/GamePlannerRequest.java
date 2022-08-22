package com.wealthpilot.dto;

import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Team;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GamePlannerRequest {

    private String league;
    private String country;
    private List<Team> teams;

    public League to(){
        return League.builder()
                .league(this.league)
                .country(this.country)
                .teams(this.teams)
                .build();
    }


}
