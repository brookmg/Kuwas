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

package app.kuwas.android.bridge.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Util {

    public static <T, U> void copyFieldsInClassToAnother(T original , U destination ) {
//        try {
//            //BeanUtils.copyProperties(destination , original);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
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
