/*
 * Copyright (C) 2019 Brook Mezgebu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.kuwas.android.bridge.data;

import java.io.Serializable;

/**
 * Created by BrookMG on 11/24/2019 in io.brookmg.soccerethiopiaapi
 * inside the project SoccerEthiopia .
 */
@SuppressWarnings("unused")
public class RankItem implements Serializable {

    private int rank; //Rank of the football team
    private Team team;
    private int points; //Team point
    private int playedGames; //The number of games the team played
    private int wonGames; //The number of games the team has won
    private int drawGames; //The number of games the team neither won nor lost
    private int lostGames; //The number of games the team has lost
    private int goalsScored; //The number of goals scored by the team
    private int goalAgainst; //The number of goals that were received by the team
    private int goalDifference; //The net difference between goalScored and goalAgainst

    public RankItem(int rank, Team team, int points, int playedGames, int wonGames, int drawGames, int lostGames) {
        this.rank = rank;
        this.team = team;
        this.points = points;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.drawGames = drawGames;
        this.lostGames = lostGames;

        //set as primitive null for non-required variables
        this.goalsScored = -1;
        this.goalDifference = -1;
        this.goalAgainst = -1;
    }

    public RankItem(int rank, Team team, int points, int playedGames, int wonGames, int drawGames, int lostGames, int goalsScored, int goalAgainst, int goalDifference) {
        this.rank = rank;
        this.team = team;
        this.points = points;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.drawGames = drawGames;
        this.lostGames = lostGames;
        this.goalsScored = goalsScored;
        this.goalAgainst = goalAgainst;
        this.goalDifference = goalDifference;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }


    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getDrawGames() {
        return drawGames;
    }

    public void setDrawGames(int drawGames) {
        this.drawGames = drawGames;
    }

    public int getLostGames() {
        return lostGames;
    }

    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalAgainst() {
        return goalAgainst;
    }

    public void setGoalAgainst(int goalAgainst) {
        this.goalAgainst = goalAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
