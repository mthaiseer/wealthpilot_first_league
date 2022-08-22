package com.wealthpilot.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Match {

    private String date;
    private String firstOpponent;
    private String secondOpponent;

}
