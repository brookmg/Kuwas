/*
 * Copyright (C) 2019 Brook Mezgebu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.kuwas.soccerethiopia.utils;

import java.util.ArrayList;
import java.util.Arrays;

import app.kuwas.android.bridge.data.Team;

/**
 * Created by BrookMG on 12/19/2018 in io.brookmg.soccerethiopiaapi.utils
 * inside the project SoccerEthiopia .
 */
@SuppressWarnings({"unused", "WeakerAccess", "SpellCheckingInspection"})
public class Constants {

    public static final String BASE_URL = "https://www.soccerethiopia.net";
    public static final String CLUB_STANDING_BASE_URL = "https://www.soccerethiopia.net/football/table/2012-standing";
    public static final String PREMIER_LEAGUE_SCHEDULE_BASE_URL = "https://www.soccerethiopia.net/ethpl-2012";
    public static final String NEWS_URL = "https://www.soccerethiopia.net/wp-json/wp/v2/posts";
    public static final String AUTHOR_URL = "https://www.soccerethiopia.net/wp-json/wp/v2/users";
    public static final String CATEGORY_URL = "https://www.soccerethiopia.net/wp-json/wp/v2/categories";
    public static final String MEDIA_URL = "https://www.soccerethiopia.net/wp-json/wp/v2/media";
    public static final String TOP_PLAYERS_BASE_URL = "https://www.soccerethiopia.net/football/list/2018-19-premier-league-top-goal-scorers";
    public enum TEAMS_ID {  ETHIOPIA_BUNA , KIDUS_GORJIS , HAWASSA_KETEMA , SIDAMA_BUNA ,
                            WELWALO , FASIL_KENEMA , BAHIR_DAR_KENEMA , MEKELE_70_ENDERTA ,
                            ADAMA_KETEMA , WELAYETA_DICHA , DIRE_DAWA_KETEMA , MEKELAKEYA ,
                            JIMMA_ABA_JIFFAR , SEHUL_SHERE , DEBUB_POLICE , DEDEBIT ,
                            HADIYA_HOSAENA , SEBETA_KETEMA , WELKITEY_KETEMA}

    /* Current League Session Teams */
    public static final Team ETHIOPIA_BUNA = new Team(TEAMS_ID.ETHIOPIA_BUNA.ordinal() , "https://www.soccerethiopia.net/football/team/ethiopia-bunna", "ኢትዮጵያ ቡና" , "https://www.soccerethiopia.net/wp-content/uploads/2016/02/Bunna-128x128.png", 1968, "Addis Abeba");
    public static final Team KIDUS_GORJIS = new Team(TEAMS_ID.KIDUS_GORJIS.ordinal() , "https://www.soccerethiopia.net/football/team/kidus-giorgis","ቅዱስ ጊዮርጊስ", "https://www.soccerethiopia.net/wp-content/uploads/2015/10/KG-128x128.png" , -1 , "", new ArrayList<>(Arrays.asList("Sanjaw" , "ቅዱስ ጊዮርጊስ"))); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team HAWASSA_KETEMA = new Team(TEAMS_ID.HAWASSA_KETEMA.ordinal(),"https://www.soccerethiopia.net/football/team/hawassa-ketema", "ሀዋሳ ከተማ", "https://www.soccerethiopia.net/wp-content/uploads/2016/05/Hawassa-128x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team SIDAMA_BUNA = new Team(TEAMS_ID.SIDAMA_BUNA.ordinal() ,"https://www.soccerethiopia.net/football/team/sidama-bunna", "ሲዳማ ቡና", "https://www.soccerethiopia.net/wp-content/uploads/2016/05/Sidama-128x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team WELWALO = new Team(TEAMS_ID.WELWALO .ordinal(),"https://www.soccerethiopia.net/football/team/wolwalo-au", "ወልዋሎ ዓ.ዩ.", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/Welwalo-128x128.png" , -1 , "", new ArrayList<>(Arrays.asList("ወልዋሎ ዓ.ዩ." , "ወልዋሎ ዓ/ዩ" , "ወልዋሎ ዓ .ዩ"))); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team FASIL_KENEMA = new Team(TEAMS_ID.FASIL_KENEMA.ordinal() ,"https://www.soccerethiopia.net/football/team/fasil-kenema", "ፋሲል ከነማ", "https://www.soccerethiopia.net/wp-content/uploads/2016/05/Fasil-128x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team BAHIR_DAR_KENEMA = new Team(TEAMS_ID.BAHIR_DAR_KENEMA.ordinal() ,"https://www.soccerethiopia.net/football/team/bahir-dar-ketema","ባህር ዳር ከተማ", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/BDK-123x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team MEKELE_70_ENDERTA = new Team(TEAMS_ID.MEKELE_70_ENDERTA .ordinal(),"https://www.soccerethiopia.net/football/team/mekelle-70-enderta", "መቐለ 70 እንደርታ", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/MK-105x128.png" , -1 , "" , new ArrayList<>(Arrays.asList("መቐለ 70", "መቐለ 70 እንደርታ" , "መቐለ 70 እ." , "መቐለ ሠ.እ."))); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team ADAMA_KETEMA = new Team(TEAMS_ID.ADAMA_KETEMA.ordinal() ,"https://www.soccerethiopia.net/football/team/adama-ketema", "አዳማ ከተማ", "https://www.soccerethiopia.net/wp-content/uploads/2016/05/Adama-1-128x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team WELAYETA_DICHA = new Team(TEAMS_ID.WELAYETA_DICHA.ordinal() ,"https://www.soccerethiopia.net/football/team/wolaitta-dicha", "ወላይታ ድቻ", "https://www.soccerethiopia.net/wp-content/uploads/2016/05/Dicha-128x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team DIRE_DAWA_KETEMA = new Team(TEAMS_ID.DIRE_DAWA_KETEMA.ordinal() ,"https://www.soccerethiopia.net/football/team/diredawa-ketema", "ድሬዳዋ ከተማ", "https://www.soccerethiopia.net/wp-content/uploads/2016/05/dire-logo.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team MEKELAKEYA = new Team(TEAMS_ID.MEKELAKEYA .ordinal(),"https://www.soccerethiopia.net/football/team/mekelakeya", "መከላከያ", "https://www.soccerethiopia.net/wp-content/uploads/2016/02/Mekelakeya-128x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team JIMMA_ABA_JIFFAR = new Team(TEAMS_ID.JIMMA_ABA_JIFFAR.ordinal() ,"https://www.soccerethiopia.net/football/team/jimma-aba-jifar", "ጅማ አባ ጅፋር", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/JAJ-1-128x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team SEHUL_SHERE = new Team(TEAMS_ID.SEHUL_SHERE.ordinal() ,"https://www.soccerethiopia.net/football/team/shire-endaselassie", "ስሑል ሽረ", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/Shire-128x118.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team DEBUB_POLICE = new Team(TEAMS_ID.DEBUB_POLICE.ordinal() ,"https://www.soccerethiopia.net/football/team/debub-police","ደቡብ ፖሊስ", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/dP-125x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team DEDEBIT = new Team(TEAMS_ID.DEDEBIT.ordinal() ,"https://www.soccerethiopia.net/football/team/dedebit", "ደደቢት", "https://www.soccerethiopia.net/wp-content/uploads/2016/02/Dedebit-128x128.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team HADIYA_HOSAENA = new Team(TEAMS_ID.HADIYA_HOSAENA.ordinal() ,"https://www.soccerethiopia.net/football/team/%e1%88%80%e1%8b%b5%e1%8b%ab-%e1%88%86%e1%88%b3%e1%8b%95%e1%8a%93", "ሀዲያ ሆሳዕና", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/24483-128x128.png" , -1 , "", new ArrayList<>(Arrays.asList("ሀዲያ ሆሳዕና" , "ሀድያ ሆሳዕና"))); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team SEBETA_KETEMA = new Team(TEAMS_ID.SEBETA_KETEMA .ordinal(),"https://www.soccerethiopia.net/football/team/sebeta", "ሰበታ ከተማ", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/sebeta.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!
    public static final Team WELKITEY_KETEMA = new Team(TEAMS_ID.WELKITEY_KETEMA.ordinal() ,"https://www.soccerethiopia.net/football/team/%e1%8b%88%e1%88%8d%e1%89%82%e1%8c%a4-%e1%8a%a8%e1%89%b0%e1%88%9b", "ወልቂጤ ከተማ", "https://www.soccerethiopia.net/wp-content/uploads/2016/11/Wolkite.png" , -1 , ""); // FIXME: 1/19/2019 CORRECT DETAILS HERE!

    public static final ArrayList<Team> teams = new ArrayList<>(Arrays.asList(Constants.KIDUS_GORJIS, Constants.ETHIOPIA_BUNA , Constants.ADAMA_KETEMA , Constants.BAHIR_DAR_KENEMA,
            Constants.DEBUB_POLICE, Constants.DEDEBIT, Constants.DIRE_DAWA_KETEMA, Constants.FASIL_KENEMA, Constants.HAWASSA_KETEMA,
            Constants.JIMMA_ABA_JIFFAR, Constants.MEKELAKEYA, Constants.MEKELE_70_ENDERTA, Constants.SEHUL_SHERE,
            Constants.SIDAMA_BUNA, Constants.WELAYETA_DICHA, Constants.WELWALO , Constants.HADIYA_HOSAENA , Constants.SEBETA_KETEMA,
            Constants.WELKITEY_KETEMA));

}
