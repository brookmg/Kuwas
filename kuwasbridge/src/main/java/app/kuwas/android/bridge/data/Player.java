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
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by BrookMG on 1/19/2019 in io.brookmg.soccerethiopiaapi.data
 * inside the project SoccerEthiopia .
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Player implements Serializable {

    private String fullName;
    private String playerLink;
    private Integer playerRank;
    private Integer number;
    private String countryCode; //ISO3166-1 ALPHA-3
    private String playerPosition; // FIXME: 2/11/2019 This should be some kind of enum
    private Team currentTeam;
    private ArrayList<Team> previousTeams = new ArrayList<>();
    private Integer goalsInThisSession;

    public Player(String fullName, String playerLink, Integer number, String countryCode, String playerPosition, Team currentTeam) {
        this(fullName, playerLink,-1, -1, number, countryCode, playerPosition, currentTeam, new ArrayList<Team>());
    }

    public Player(String fullName, String playerLink, Integer playerRank, Integer goalsInThisSession, Integer number, String countryCode, String playerPosition, Team currentTeam) {
        this(fullName, playerLink,playerRank, goalsInThisSession, number, countryCode, playerPosition, currentTeam, new ArrayList<Team>());
    }

    public Player(String fullName, String playerLink, Integer playerRank, Integer goalsInThisSession, Integer number, String countryCode, String playerPosition, Team currentTeam, ArrayList<Team> previousTeams) {
        this(fullName, playerLink, playerRank, number, countryCode, playerPosition, currentTeam, previousTeams, goalsInThisSession);
    }

    public Player(String fullName, String playerLink, Integer playerRank, Integer number, String countryCode, String playerPosition, Team currentTeam, ArrayList<Team> previousTeams, Integer goalsInThisSession) {
        this.fullName = fullName;
        this.playerLink = playerLink;
        this.playerRank = playerRank;
        this.number = number;
        this.countryCode = countryCode;
        this.playerPosition = playerPosition;
        this.currentTeam = currentTeam;
        this.previousTeams = previousTeams;
        this.goalsInThisSession = goalsInThisSession;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public ArrayList<Team> getPreviousTeams() {
        return previousTeams;
    }

    public void setPreviousTeams(ArrayList<Team> previousTeams) {
        this.previousTeams = previousTeams;
    }

    public String getPlayerLink() {
        return playerLink;
    }

    public void setPlayerLink(String playerLink) {
        this.playerLink = playerLink;
    }

    public Integer getPlayerRank() {
        return playerRank;
    }

    public void setPlayerRank(Integer playerRank) {
        this.playerRank = playerRank;
    }

    public Integer getGoalsInThisSession() {
        return goalsInThisSession;
    }

    public void setGoalsInThisSession(Integer goalsInThisSession) {
        this.goalsInThisSession = goalsInThisSession;
    }

    public void setDetailOfThisPlayer(Player p) {
        //add a method to get player detail here
        setFullName(p.getFullName());
        setNumber(p.getNumber());
        setCurrentTeam(p.getCurrentTeam());
        setCountryCode(p.getCountryCode());
        setPlayerPosition(p.getPlayerPosition());
    }

    @NonNull
    @Override
    public String toString() {
        return "PlayerName: " + fullName + ", PlayerRank: " + playerRank + ", PlayerGoals: " + goalsInThisSession + ", currentTeam: " + currentTeam + ", CountryCode: " + countryCode + ", Number:" + number + ", Position: " + playerPosition;
    }
}
