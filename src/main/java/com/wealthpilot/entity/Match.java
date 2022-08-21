package com.wealthpilot.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Match {

    private String date;
    private String firstOpponent;
    private String secondOpponent;

}
