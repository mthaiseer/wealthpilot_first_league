package com.wealthpilot.entity;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class League {
    private String league;
    private String country;
    private List<Team> teams;
}
