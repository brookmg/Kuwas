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

package app.kuwas.soccerethiopia.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.kuwas.android.bridge.data.LeagueScheduleItem;
import app.kuwas.android.bridge.data.Team;
import app.kuwas.soccerethiopia.utils.Utils;

/**
 * Created by BrookMG on 1/30/2020 in app.kuwas.soccerethiopia.network
 * inside the project Kuwas .
 */
public class LeagueScheduleFetch {

    public static List<LeagueScheduleItem> parseServerResponseToLeagueScheduleItemList(String jsonResponse)
            throws JSONException {
        List<LeagueScheduleItem> returnable = new ArrayList<>();

        JSONArray mainArray = new JSONArray(jsonResponse);
        for (int i = 0; i < mainArray.length(); i++) {
            JSONObject scheduleObject = mainArray.getJSONObject(i);

            JSONObject teamOne = scheduleObject.getJSONObject("team_one");
            JSONObject teamTwo = scheduleObject.getJSONObject("team_two");

            int gameStatus = scheduleObject.getInt("game_status");

            switch (gameStatus) {
                case 0: gameStatus = 101; break; //Took place
                case 3: default: gameStatus = 104; break; //NORMAL
            }

            int week = scheduleObject.getInt("week");

            String gameDate = scheduleObject.getString("game_date");

            Map<Team, Integer> teamIntegerMap = new HashMap<>();
            teamIntegerMap.put(
                    Utils.getTeamFromTeamName(teamOne.getString("name")),
                    teamOne.getInt("score")
            );

            teamIntegerMap.put(
                    Utils.getTeamFromTeamName(teamTwo.getString("name")),
                    teamTwo.getInt("score")
            );

            LeagueScheduleItem scheduleItem = new LeagueScheduleItem(
                    week,
                    gameDate,
                    gameStatus,
                    teamIntegerMap
            );

            returnable.add(scheduleItem);
        }

        return returnable;
    }



}
