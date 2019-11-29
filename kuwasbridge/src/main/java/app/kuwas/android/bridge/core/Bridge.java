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

package app.kuwas.android.bridge.core;

import java.util.ArrayList;

import app.kuwas.android.bridge.data.LeagueScheduleItem;
import app.kuwas.android.bridge.data.NewsItem;
import app.kuwas.android.bridge.data.Player;
import app.kuwas.android.bridge.data.RankItem;
import app.kuwas.android.bridge.data.Team;

public interface Bridge {
    void getLatestTeamRanking (OnItemProcessed<ArrayList<RankItem>> processed, OnError error);
    void getLatestTeamRanking (OnItemProcessed<ArrayList<RankItem>> processed, OnError error, boolean moreDetailed);
    void getLeagueSchedule (OnItemProcessed<ArrayList<LeagueScheduleItem>> processed , OnError error);
    void getLeagueScheduleOfWeek ( int week , OnItemProcessed<ArrayList<LeagueScheduleItem>> processed , OnError error);
    void getThisWeekLeagueSchedule ( OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error);
    void getLastWeekLeagueSchedule ( OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error);
    void getNextWeekLeagueSchedule ( OnItemProcessed<ArrayList<LeagueScheduleItem>> processed, OnError error);
    void getTeamDetail (Team incomplete, OnItemProcessed<Team> teamDetailReady, OnError error);
    void getNextGameOfTeam (Team team , OnItemProcessed<LeagueScheduleItem> callback, OnError onError);
    void getNextGameOfTeamInThisWeek (Team team , OnItemProcessed<LeagueScheduleItem> callback, OnError onError);
    void getTopPlayers (OnItemProcessed<ArrayList<Player>> onPlayersListReceived, OnError onError);
    void getPlayerDetail (Player player, OnItemProcessed<Player> callback, OnError onError);
    void getLatestNews(OnItemProcessed<ArrayList<NewsItem>> onNewsDataProcessed, OnError error);
    void getNewsItemContent(NewsItem item, OnItemProcessed<NewsItem> onNewsItemProcessed, OnError error);
}
