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

package app.kuwas.android.bridge.data;

import androidx.annotation.IntDef;

/**
 * Created by BrookMG on 12/29/2018 in io.brookmg.soccerethiopiaapi.data
 * inside the project SoccerEthiopia .
 */
public class LeagueItemStatus {
    @IntDef(value = {STATUS_TOOK_PLACE , STATUS_POSTPONED, STATUS_CANCELLED, STATUS_NORMAL})
    public @interface GameStatus{}

    public static final int STATUS_TOOK_PLACE = 101;
    public static final int STATUS_CANCELLED = 102;
    public static final int STATUS_POSTPONED = 103;
    public static final int STATUS_NORMAL = 104;
}
