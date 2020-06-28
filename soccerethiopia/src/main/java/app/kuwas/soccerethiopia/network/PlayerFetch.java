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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import app.kuwas.android.bridge.data.Player;
import app.kuwas.android.bridge.data.Team;
import app.kuwas.soccerethiopia.utils.Utils;

public class PlayerFetch {

    public static List<Player> parseTopPlayerListFromAPI(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Player> deserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();
            Team team = Utils.getTeamFromTeamName(jsonObject.get("team").getAsString());

            return new Player(
                    jsonObject.get("player").getAsString(),
                    "",
                    -1,
                    "ET",
                    "---",
                    team
            );
        };

        gsonBuilder.registerTypeAdapter(Player.class, deserializer);

        Gson customGson = gsonBuilder.create();
        return Arrays.asList(customGson.fromJson(response, Player[].class));
    }

}
