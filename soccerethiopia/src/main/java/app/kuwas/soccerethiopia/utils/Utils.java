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

package app.kuwas.soccerethiopia.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.kuwas.android.bridge.data.Team;

import static app.kuwas.soccerethiopia.utils.Constants.teams;


/**
 * Created by BrookMG on 1/19/2019 in io.brookmg.soccerethiopiaapi.utils
 * inside the project SoccerEthiopia .
 */
public class Utils {

    private static String customTrim (String orig) {
        return orig.replace(" ", "").replace(".", "");
    }

    @Nullable
    public static Team getTeamFromTeamName (String teamName) {
        for (Team team : teams) {
            //There is a huge inconsistency on the team names. This has made it hard to make
            //a simple comparision between team names to identify which team it is.
            //For example : ወልዋሎ ዓ.ዩ. can be ወልዋሎ ዓ/ዩ somewhere or ወልዋሎ ዓ. ዩ. somewhere
            //else on the site... I have tried to handle these conditions with keywords property
            //in the Team structure... until some better way comes along.

            for (String keyword : team.getKeywords()) {
                if (customTrim(keyword).equals(customTrim(teamName))) return team;
            }

        }

        // Violently throwing an exception might not be the best solution as there are a lot of
        // teams to consider if we want to add them to the list
        // throw new TeamNotFoundException("Team " + teamName + " is not found in our current data-set.");
        return null;
    }

    @Nullable
    public static String getCountryNameFromISO3 (@NonNull Context context, @NonNull String countryCode) {
        try {
            countryCode = countryCode.toUpperCase();
            JSONArray jsonArray = new JSONArray(getCountryCodeContent(context));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray innerArray = jsonArray.getJSONArray(i);
                if (innerArray.getString(2).equals(countryCode)) return innerArray.getString(0);
            }
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static String getCountryISO2FromISO3 (@NonNull Context context, @NonNull String countryCode) {
        try {
            countryCode = countryCode.toUpperCase();
            JSONArray jsonArray = new JSONArray(getCountryCodeContent(context));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray innerArray = jsonArray.getJSONArray(i);
                if (innerArray.getString(2).equals(countryCode)) return innerArray.getString(1);
            }
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getCountryCodeContent(Context context) {
        try {
            InputStream leagueAsset = context.getAssets().open("countrycode.json");
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(leagueAsset));
            String line;

            while(( line = reader.readLine()) != null ) {
                builder.append(line);
            }

            reader.close();
            leagueAsset.close();
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
