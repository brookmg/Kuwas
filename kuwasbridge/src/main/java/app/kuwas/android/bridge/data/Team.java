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
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by BrookMG on 1/19/2019 in io.brookmg.soccerethiopiaapi.data
 * inside the project SoccerEthiopia .
 */
@SuppressWarnings("unused")
public class Team implements Serializable {

    private int teamId;

    private String teamLink;
    private String teamFullName;
    private String teamLogo;
    private Integer initYear;
    private String fromCity;
    private ArrayList<String> previousNames;
    private String stadium;

    private String president;
    private String vicePresident;
    private String manager;

    private String mainCoach;
    private String viceCoach;
    private String techniqueDirector;

    private String goalKeeper;
    private String teamAlpha;
    private String teamNurse;

    private ArrayList<String> keywords = new ArrayList<>(); //to avoid NullPointerExceptions ... Don't worry we will move to kot one day

    public Team(Integer id, String teamLink, String teamFullName, String teamLogo, Integer initYear, String fromCity) {
        this(id, teamLink, teamFullName, teamLogo, initYear, fromCity, new ArrayList<>(Collections.singletonList(teamFullName)));
    }

    public Team(Integer id, String teamLink, String teamFullName, String teamLogo, Integer initYear, String fromCity, ArrayList<String> keywords) {
        this(id ,teamLink, teamFullName, teamLogo, initYear, fromCity,"", "" , "", "" , keywords);
    }

    public Team(Integer id, String teamLink, String teamFullName, String teamLogo, Integer initYear,
                String fromCity, String stadium, String president, String mainCoach, String teamAlpha,
                ArrayList<String> keywords) {
        this(id, teamLink, teamFullName, teamLogo, initYear, fromCity, new ArrayList<String>(), stadium, president,"", "", mainCoach, "", "","", teamAlpha, "", keywords);
    }

    public Team(Integer teamId, String teamLink, String teamFullName, String teamLogo,
                Integer initYear, String fromCity, ArrayList<String> previousNames,
                String stadium, String president, String vicePresident, String manager,
                String mainCoach, String viceCoach, String techniqueDirector,
                String goalKeeper, String teamAlpha, String teamNurse, ArrayList<String> keywords) {
        this.teamId = teamId;
        this.teamLink = teamLink;
        this.teamFullName = teamFullName;
        this.teamLogo = teamLogo;
        this.initYear = initYear;
        this.fromCity = fromCity;
        this.previousNames = previousNames;
        this.stadium = stadium;
        this.president = president;
        this.vicePresident = vicePresident;
        this.manager = manager;
        this.mainCoach = mainCoach;
        this.viceCoach = viceCoach;
        this.techniqueDirector = techniqueDirector;
        this.goalKeeper = goalKeeper;
        this.teamAlpha = teamAlpha;
        this.teamNurse = teamNurse;
        this.keywords = keywords;
    }

    public String getTeamFullName() {
        return teamFullName;
    }

    public void setTeamFullName(String teamFullName) {
        this.teamFullName = teamFullName;
    }

    public Integer getInitYear() {
        return initYear;
    }

    public void setInitYear(Integer initYear) {
        this.initYear = initYear;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public ArrayList<String> getPreviousNames() {
        return previousNames;
    }

    public void setPreviousNames(ArrayList<String> previousNames) {
        this.previousNames = previousNames;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getVicePresident() {
        return vicePresident;
    }

    public void setVicePresident(String vicePresident) {
        this.vicePresident = vicePresident;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMainCoach() {
        return mainCoach;
    }

    public void setMainCoach(String mainCoach) {
        this.mainCoach = mainCoach;
    }

    public String getViceCoach() {
        return viceCoach;
    }

    public void setViceCoach(String viceCoach) {
        this.viceCoach = viceCoach;
    }

    public String getTechniqueDirector() {
        return techniqueDirector;
    }

    public void setTechniqueDirector(String techniqueDirector) {
        this.techniqueDirector = techniqueDirector;
    }

    public String getGoalKeeper() {
        return goalKeeper;
    }

    public void setGoalKeeper(String goalKeeper) {
        this.goalKeeper = goalKeeper;
    }

    public String getTeamAlpha() {
        return teamAlpha;
    }

    public void setTeamAlpha(String teamAlpha) {
        this.teamAlpha = teamAlpha;
    }

    public String getTeamNurse() {
        return teamNurse;
    }

    public void setTeamNurse(String teamNurse) {
        this.teamNurse = teamNurse;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public void addKeyword (String keyword) {
        this.keywords.add(keyword);
    }

    public void removeKeyword (String keyword) {
        this.keywords.remove(keyword);
    }

    public String getTeamLink() {
        return teamLink;
    }

    public void setTeamLink(String teamLink) {
        this.teamLink = teamLink;
    }

    public boolean isComplete () {
        return teamId != 0 && !teamLogo.isEmpty() &&
                !teamAlpha.isEmpty() && !teamFullName.isEmpty()
                && !teamLink.isEmpty() && !teamNurse.isEmpty()
                && !fromCity.isEmpty() && initYear != null
                && !keywords.isEmpty() && !goalKeeper.isEmpty()
                && !techniqueDirector.isEmpty() && !mainCoach.isEmpty()
                && !viceCoach.isEmpty() && !manager.isEmpty()
                && !president.isEmpty() && !previousNames.isEmpty();
    }

    @NonNull
    @Override
    public String toString() {
        return "[Name: " + teamFullName + ", From: " + fromCity + ", Since: " + initYear + ", President: " + president
                + ", GoalKeeper: " + goalKeeper + ", MainCoach: " + mainCoach + ", viceCoach: " + viceCoach
                + ", Technique_Director: " + techniqueDirector + ", Manager: " + manager + ", Nurse: " + teamNurse
                + ", previousNames: " + Arrays.toString(previousNames.toArray()) + "]";
    }
}
