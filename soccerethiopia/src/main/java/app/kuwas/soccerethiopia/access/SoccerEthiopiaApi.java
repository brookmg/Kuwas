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

package app.kuwas.soccerethiopia.access;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;

import app.kuwas.android.bridge.core.Bridge;
import app.kuwas.android.bridge.core.OnError;
import app.kuwas.android.bridge.core.OnItemProcessed;
import app.kuwas.android.bridge.data.LeagueScheduleItem;
import app.kuwas.android.bridge.data.NewsItem;
import app.kuwas.android.bridge.data.Player;
import app.kuwas.android.bridge.data.RankItem;
import app.kuwas.android.bridge.data.Team;
import app.kuwas.soccerethiopia.network.CachedStringRequest;

import static app.kuwas.soccerethiopia.network.LeagueScheduleFetch.parseServerResponseToLeagueScheduleItemList;
import static app.kuwas.soccerethiopia.network.NewsFetch.parseNewsItemsFromAPIData;
import static app.kuwas.soccerethiopia.network.StandingFetch.parseOutStandingDataFromSoccerEtAPI;


/**
 * Created by BrookMG on 12/20/2018 in io.brookmg.soccerethiopiaapi.access
 * inside the project SoccerEthiopia .
 */
@SuppressWarnings("unused")
public class SoccerEthiopiaApi implements Bridge {

    private RequestQueue mainRequestQueue;
    private Boolean contentShouldBeCached;

    /**
     * Main Constructor for Soccer Ethiopia API
     * @param context - used for creating the main request queue. consider using {@code getApplicationContext()}
     * @throws NullPointerException if the context is null
     */
    public SoccerEthiopiaApi(@NonNull Context context) {
        this(context, true);
    }

    /**
     * Main Constructor for Soccer Ethiopia API
     * @param context - used for creating the main request queue. consider using {@code getApplicationContext()}
     * @param shouldCache - to specify whether to receive cached content or not, which is by default true
     */
    public SoccerEthiopiaApi(@NonNull Context context, boolean shouldCache) {
        mainRequestQueue = Volley.newRequestQueue(context);
        contentShouldBeCached = shouldCache;
    }

    /**
     * Overloaded constructor in-case the user wants to use fragment as parameter
     * @param fragment {@code fragment.getActivity()} will be used as a context
     * @deprecated using contexts with lifecycle might lead to some leaks for now. Use {@code SoccerEthiopiaApi(getApplicationContext())} instead
     */
    @Deprecated
    public SoccerEthiopiaApi(Fragment fragment) {
        this(fragment.getActivity());
    }


    @Override
    public void getLatestTeamRanking(OnItemProcessed<ArrayList<RankItem>> processed, OnError error) {

    }

    @Override
    public void getLatestTeamRanking(OnItemProcessed<ArrayList<RankItem>> processed, OnError error, boolean moreDetailed) {
        mainRequestQueue.add(new StringRequest(Request.Method.GET , "https://socceret.herokuapp.com/standing" ,
                res -> {
                    try {
                        processed.onFinish(new ArrayList<>(parseOutStandingDataFromSoccerEtAPI(res)));
                    } catch (JSONException e) {
                        error.onError(e.getMessage());
                        e.printStackTrace();
                    }
                },
                err -> error.onError(err.toString())));
    }

    @Override
    public void getLeagueSchedule(OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {

    }

    @Override
    public void getLeagueScheduleOfWeek(int week, OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {

    }

    @Override
    public void getThisWeekLeagueSchedule(OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {
        mainRequestQueue.add(new StringRequest(Request.Method.GET ,
                "https://socceret.herokuapp.com/thisweek/schedule" , res -> {
            try {
                processed.onFinish(
                        new ArrayList<>(parseServerResponseToLeagueScheduleItemList(res))
                );
            } catch (JSONException e) {
                e.printStackTrace();
                error.onError(e.toString());
            }
        }, err -> error.onError(err.toString())));
    }

    @Override
    public void getLastWeekLeagueSchedule(OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {

    }

    @Override
    public void getNextWeekLeagueSchedule(OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {

    }

    @Override
    public void getTeamDetail(Team incomplete, OnItemProcessed<Team> teamDetailReady, OnError error) {

    }

    @Override
    public void getNextGameOfTeam(Team team, OnItemProcessed<LeagueScheduleItem> callback, OnError onError) {

    }

    @Override
    public void getNextGameOfTeamInThisWeek(Team team, OnItemProcessed<LeagueScheduleItem> callback, OnError onError) {

    }

    @Override
    public void getTopPlayers(OnItemProcessed<ArrayList<Player>> onPlayersListReceived, OnError onError) {

    }

    @Override
    public void getPlayerDetail(Player player, OnItemProcessed<Player> callback, OnError onError) {

    }

    @Override
    public void getLatestNews(OnItemProcessed<ArrayList<NewsItem>> onNewsDataProcessed, OnError error) {
        StringRequest request = new CachedStringRequest(Request.Method.GET ,
                "https://socceret.herokuapp.com/news" ,
                res -> onNewsDataProcessed.onFinish(new ArrayList<>(parseNewsItemsFromAPIData(res))),
                err -> error.onError(err.toString()));

        request.setRetryPolicy(new DefaultRetryPolicy(
                40_000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        mainRequestQueue.add(request);
    }

    @Override
    public void getNewsItemContent(NewsItem item, OnItemProcessed<NewsItem> onNewsItemProcessed, OnError error) {

    }
}
