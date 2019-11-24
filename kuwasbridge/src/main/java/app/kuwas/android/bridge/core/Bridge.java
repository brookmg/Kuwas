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

public interface Bridge {

    /**
     * Main Function to get the latest team ranking
     * @param processed - a callback to handle the processed array-list
     * @param error - a callback to handle any error
     */
    void getLatestTeamRanking (OnStandingDataProcessed processed, OnError error);

    /**
     * Main Function to get the latest team ranking
     * @param processed - a callback to handle the processed array-list
     * @param error - a callback to handle any error
     * @param moreDetailed - whether to include details like `goalAgainst` for each RankItem
     */
    void getLatestTeamRanking (OnStandingDataProcessed processed, OnError error, boolean moreDetailed);

    /**
     * Main Function to get all the league schedule for current session
     * @param processed - a callback to handle the processed array-list
     * @param error - a callback to handle any error
     */
    void getLeagueSchedule (OnLeagueScheduleDataProcessed processed , OnError error)
}
