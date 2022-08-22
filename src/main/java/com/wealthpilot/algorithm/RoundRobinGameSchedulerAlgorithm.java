package com.wealthpilot.algorithm;

import com.wealthpilot.entity.League;
import com.wealthpilot.entity.Match;
import com.wealthpilot.entity.Team;
import com.wealthpilot.exception.GameScheduleException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static com.wealthpilot.constant.GameMessages.INVALID_REQUEST_RECEIVED;
import static com.wealthpilot.constant.GameMessages.NUMBER_OF_LEGS;


/**
 * @Author Mohamed Thaiseer
 * This class responsible for creating match plan as per round-robin tournament
 * Logic is total team spit into 2 group and create one match. for next round
 * except first team (home team), all other teams(away teams) are rotated
 */

@Service
public class RoundRobinGameSchedulerAlgorithm implements GameSchedulerAlgorithm {


    private League league;
    private  int skipWeekByOne = 1;
    LocalDate localDate = null;

    private List<Match> matches = null;

    /**
     * This class responsible to generate a game plan
     * if there are n team then n-1 rounds in one leg. Also, there are (n * (n-1) /2)  matches in each league
     * this process repeat for number of leg, as per requirement teams swapped each other
     */
    @Override
    public void  createGamePlans(League league, boolean isSingleMatchPerDay) throws GameScheduleException {

        validateRequest(league);
        List<Team> teams = league.getTeams();
        int teamSize = teams.size();
        addEmptyTeamNeeded(teams, teamSize);
        int rounds = teams.size()-1;
        matches = new ArrayList<>();
        localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        createSchedulePerLeg(isSingleMatchPerDay, teams, rounds);
    }

    /**
     *
     * @param isSingleMatchPerDay
     * @param teams
     * @param rounds
     */
    private void createSchedulePerLeg(boolean isSingleMatchPerDay, List<Team> teams, int rounds) {
        for(int leg =0; leg < NUMBER_OF_LEGS; leg++){
            for(int round = 1; round<= rounds; round++){
                createOneRound(isSingleMatchPerDay, leg , round, teams);
                rotateLastTeam(teams);
            }
            //Add 3 week gap after each leg
            localDate =   localDate.plusWeeks(3);
        }
    }

    /**
     *
     * @param league
     * @throws GameScheduleException
     */
    private void validateRequest(League league) throws GameScheduleException {
        if(league == null || league.getTeams()  == null ){
            throw new GameScheduleException(INVALID_REQUEST_RECEIVED);
        }
    }


    /**
     * This method is responsible to return final outcome
     * @return
     */
    @Override
    public List<Match> getFinalSchedule() {
        return matches;
    }

    /**
     * This method is  responsible to create a match for n rounds. As result (n * (n-1) /2)  matches generated
     *
     * @param isOnePlay
     * @param leg
     * @param round
     * @param teamList
     */
    private void createOneRound(boolean isOnePlay, int leg, int round, List<Team> teamList) {
        int partition = teamList.size()/2;

        List<Team> firstGroup = new ArrayList<>();
        List<Team> secondGroup = new ArrayList<>();

        int size = teamList.size();
        createFirstGroup(teamList, partition, firstGroup);
        createSecondGroup(teamList, partition, secondGroup, size);

        for(int count=0; count< firstGroup.size(); count++){

            if(isTeamContainsDummyTeam(firstGroup, secondGroup, count)){
                continue;
            }
            /**Swap teams in alternative legs as per requirements **/
            if(isTeamSwappingForAlternativeLeg(leg)){
                createMatch(isOnePlay,
                        firstGroup.get(count).getName(),
                        secondGroup.get(count).getName(),
                        count);
            }else{

                createMatch(isOnePlay,
                        secondGroup.get(count).getName(),
                        firstGroup.get(count).getName(),
                        count);
            }
        }
    }

    /**
     *
     * @param firstGroup
     * @param secondGroup
     * @param count
     * @return
     */
    private boolean isTeamContainsDummyTeam(List<Team> firstGroup, List<Team> secondGroup, int count) {
        return firstGroup.get(count) == null || secondGroup.get(count) == null;
    }

    /**
     *
     * @param leg
     * @return
     */
    private boolean isTeamSwappingForAlternativeLeg(int leg){
        return leg % 2 ==0;
    }

    /**
     *
     * @param isOnePlay
     * @param firstOpponent
     * @param secondOpponent
     * @param count
     */
    private void createMatch(boolean isOnePlay, String firstOpponent, String secondOpponent,  int count) {
        matches.add(new Match(getNextSunday(isOnePlay? skipWeekByOne++: 1),
                firstOpponent,
                secondOpponent
                )
        );
    }

    /**
     *
     * @param teamList
     * @param mid
     * @param secondGroup
     * @param size
     */
    private void createSecondGroup(List<Team> teamList, int mid, List<Team> secondGroup, int size) {
        for(int i = size -1; i>= mid; i--){
            secondGroup.add(teamList.get(i));
        }
    }

    /**
     *
     * @param teamList
     * @param mid
     * @param firstGroup
     */
    private void createFirstGroup(List<Team> teamList, int mid, List<Team> firstGroup) {
        for(int i = 0; i< mid; i++){
            firstGroup.add(teamList.get(i));
        }
    }

    /**
     *
     * @param changeWeek
     * @return
     */
    private  String getNextSunday(int changeWeek){

        LocalDate   nextSaturday =   localDate
                .plusWeeks(changeWeek)
                .with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        return nextSaturday.toString();
    }


    /**
     * This method responsible to rotate all away team
     * @param teams
     */
    private void rotateLastTeam(List<Team> teams) {
        teams.add(1, teams.get(teams.size()-1));
        teams.remove(teams.size()-1);
    }

    /**
     *
     * @param teams
     * @param teamSize
     */
    private void addEmptyTeamNeeded(List<Team> teams, int teamSize) {
        if(teamSize % 2  ==1 ){
            teams.add(null);
        }
    }
}
