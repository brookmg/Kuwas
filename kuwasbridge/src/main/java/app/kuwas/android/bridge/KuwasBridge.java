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

package app.kuwas.android.bridge;

import android.content.Context;

import java.util.ArrayList;

import app.kuwas.android.bridge.core.Bridge;
import app.kuwas.android.bridge.core.OnError;
import app.kuwas.android.bridge.core.OnItemProcessed;
import app.kuwas.android.bridge.data.LeagueScheduleItem;
import app.kuwas.android.bridge.data.NewsItem;
import app.kuwas.android.bridge.data.Player;
import app.kuwas.android.bridge.data.RankItem;
import app.kuwas.android.bridge.data.Team;
import app.kuwas.android.bridge.leagues.SoccerEthiopiaConnector;

public class KuwasBridge implements Bridge {

    private Bridge currentlyActiveLeague;

    private static Bridge generateLeagueBridgeClass(Context context, String currentlyActiveLeague,
                                                    boolean shouldCache) {
        switch (currentlyActiveLeague) {
            case "Ethiopia" : return new SoccerEthiopiaConnector(context, shouldCache);
            default: return new SoccerEthiopiaConnector(context , shouldCache);
        }
    }

    public KuwasBridge(Context context, String currentlyActiveLeague, boolean shouldCache) {
        this.currentlyActiveLeague = generateLeagueBridgeClass(context, currentlyActiveLeague, shouldCache);
    }

    @Override
    public void getLatestTeamRanking(OnItemProcessed<ArrayList<RankItem>> processed, OnError error) {
        currentlyActiveLeague.getLatestTeamRanking(processed, error);
    }

    @Override
    public void getLatestTeamRanking(OnItemProcessed<ArrayList<RankItem>> processed, OnError error, boolean moreDetailed) {
        currentlyActiveLeague.getLatestTeamRanking(processed, error, moreDetailed);
    }

    @Override
    public void getLeagueSchedule(OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {
        currentlyActiveLeague.getLeagueSchedule(processed, error);
    }

    @Override
    public void getLeagueScheduleOfWeek(int week, OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {
        currentlyActiveLeague.getLeagueScheduleOfWeek(week, processed, error);
    }

    @Override
    public void getThisWeekLeagueSchedule(OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {
        currentlyActiveLeague.getThisWeekLeagueSchedule(processed, error);
    }

    @Override
    public void getLastWeekLeagueSchedule(OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {
        currentlyActiveLeague.getLastWeekLeagueSchedule(processed, error);
    }

    @Override
    public void getNextWeekLeagueSchedule(OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error) {
        currentlyActiveLeague.getNextWeekLeagueSchedule(processed , error);
    }

    @Override
    public void getTeamDetail(Team incomplete, OnItemProcessed<Team> teamDetailReady, OnError error) {
        currentlyActiveLeague.getTeamDetail(incomplete, teamDetailReady , error);
    }

    @Override
    public void getNextGameOfTeam(Team team, OnItemProcessed<LeagueScheduleItem> callback, OnError onError) {
        currentlyActiveLeague.getNextGameOfTeam(team, callback, onError);
    }

    @Override
    public void getNextGameOfTeamInThisWeek(Team team, OnItemProcessed<LeagueScheduleItem> callback, OnError onError) {
        currentlyActiveLeague.getNextGameOfTeamInThisWeek(team, callback, onError);
    }

    @Override
    public void getTopPlayers(OnItemProcessed<ArrayList<Player>> onPlayersListReceived, OnError onError) {
        currentlyActiveLeague.getTopPlayers(onPlayersListReceived, onError);
    }

    @Override
    public void getPlayerDetail(Player player, OnItemProcessed<Player> callback, OnError onError) {
        currentlyActiveLeague.getPlayerDetail(player, callback, onError);
    }

    @Override
    public void getLatestNews(OnItemProcessed<ArrayList<NewsItem>> onNewsDataProcessed, OnError error) {
        currentlyActiveLeague.getLatestNews(onNewsDataProcessed, error);
    }

    @Override
    public void getNewsItemContent(NewsItem item, OnItemProcessed<NewsItem> onNewsItemProcessed, OnError error) {
        currentlyActiveLeague.getNewsItemContent(item, onNewsItemProcessed, error);
    }
}
