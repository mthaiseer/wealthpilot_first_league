package com.wealthpilot.dto;

import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Team;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GamePlannerRequest {

    @NotNull(message = "League value cannot be empty")
    private String league;

    @NotNull(message = "Country value cannot be empty")
    private String country;

    @NotNull(message = "Teams list value cannot be empty")
    private List<Team> teams;

    public League to(){
        return League.builder()
                .league(this.league)
                .country(this.country)
                .teams(this.teams)
                .build();
    }


}
