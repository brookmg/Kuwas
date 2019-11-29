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
import java.util.Map;
import app.kuwas.android.bridge.error.TeamNotFoundException;

/**
 * Created by BrookMG on 12/29/2018 in io.brookmg.soccerethiopiaapi.data
 * inside the project SoccerEthiopia .
 */
@SuppressWarnings("unused")
public class LeagueScheduleItem implements Serializable {
    private int gameWeek;
    private String gameDate;
    private int gameStatus;
    private Map<Team , Integer> gameDetail;

    public LeagueScheduleItem(int gameWeek, String gameDate, @LeagueItemStatus.GameStatus int gameStatus, Map<Team, Integer> gameDetail) {
        this.gameWeek = gameWeek;
        this.gameDate = gameDate;
        this.gameStatus = gameStatus;
        this.gameDetail = gameDetail;
    }

    public int getGameWeek() {
        return gameWeek;
    }

    public void setGameWeek(int gameWeek) {
        this.gameWeek = gameWeek;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Map<Team, Integer> getGameDetail() {
        return gameDetail;
    }

    public void setGameDetail(Map<Team, Integer> gameDetail) {
        this.gameDetail = gameDetail;
    }

    public void setTeamScore(Team team , int score) {
        if (gameDetail != null && gameDetail.containsKey(team))
            gameDetail.put(team , score);
    }

    public Integer getTeamScore(Team team) throws TeamNotFoundException {
        if (gameDetail != null && gameDetail.containsKey(team))
            return gameDetail.get(team);
        throw new TeamNotFoundException("Team " + team.getTeamFullName() + " was not found on this item");
    }

    public boolean teamExists (Team team) {
        return gameDetail != null && gameDetail.containsKey(team);
    }
}
