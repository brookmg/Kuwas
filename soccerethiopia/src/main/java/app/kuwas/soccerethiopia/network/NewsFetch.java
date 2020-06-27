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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.kuwas.android.bridge.data.NewsItem;

public class NewsFetch {

    public static List<NewsItem> parseNewsItemsFromAPIData(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<NewsItem> deserializer = (json, typeOfT, context) -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            JsonObject jsonObject = json.getAsJsonObject();
            Date date = new Date();
            ArrayList<String> tags = new ArrayList<>();

            try {
               date = format.parse(jsonObject.get("newsPublishedOn").getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (JsonElement tagElement : jsonObject.get("newsTags").getAsJsonArray())
                tags.add(tagElement.getAsString());

            return new NewsItem(
                jsonObject.get("newsId").getAsInt(),
                jsonObject.get("newsImage").getAsString(),
                jsonObject.get("newsTitle").getAsString(),
                jsonObject.get("newsAuthorLink").getAsString(),
                jsonObject.get("newsAuthorName").getAsString(),
                tags,
                date,
                jsonObject.get("newsContent").getAsString()
            );
        };

        gsonBuilder.registerTypeAdapter(NewsItem.class, deserializer);
        Gson customGson = gsonBuilder.create();
        return Arrays.asList(customGson.fromJson(response, NewsItem[].class));
    }

}
