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

import app.kuwas.android.bridge.data.RankItem;
import app.kuwas.android.bridge.data.Team;
import app.kuwas.soccerethiopia.utils.Utils;

public class StandingFetch {

    public static ArrayList<RankItem> parseOutStandingDataFromSoccerEtAPI(String jsonResponse) throws JSONException {
        ArrayList<RankItem> returnable = new ArrayList<>();
        JSONArray mainArray = new JSONArray(jsonResponse);

        for (int i = 0; i < mainArray.length(); i++) {
            JSONObject rankObject = mainArray.getJSONObject(i);
            Team team = Utils.getTeamFromTeamName(rankObject.getString("team_name"));

            RankItem rankItem = new RankItem(
                    rankObject.getInt("rank"),
                    team,
                    rankObject.getInt("points"),
                    rankObject.getInt("played"),
                    rankObject.getInt("won"),
                    rankObject.getInt("draw"),
                    rankObject.getInt("lost"),
                    rankObject.getInt("goal_scored"),
                    rankObject.getInt("goal_received"),
                    rankObject.getInt("diff")
            );

            returnable.add(rankItem);
        }

        return returnable;
    }

}
