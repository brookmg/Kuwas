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

package app.kuwas.android.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.ui.adapters.TeamInformationAdapter;
import app.kuwas.android.utils.Utils;
import io.brookmg.soccerethiopiaapi.data.Team;

public class TeamInformation extends AppCompatActivity {

    RecyclerView mainContentRecyclerView;
    AppBarLayout mainAppBar;
    AppCompatImageView backButton, teamIcon;
    AppCompatTextView teamFullName;
    Team currentTeamShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Utils.getCurrentTheme(this) == 0 ? R.style.KuwasLightTheme : R.style.KuwasDarkTheme);
        setContentView(R.layout.activity_team_information);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            );

        teamFullName = findViewById(R.id.team_name);
        backButton = findViewById(R.id.back_button);
        teamIcon = findViewById(R.id.team_icon);
        mainAppBar = findViewById(R.id.top_bar_container);
        mainContentRecyclerView = findViewById(R.id.main_content_recycler_view);

        try {
            if (getIntent().getSerializableExtra("team") != null) {
                currentTeamShowing = ((Team) getIntent().getSerializableExtra("team"));
            }
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }

        teamFullName.setText(currentTeamShowing.getTeamFullName());
        Glide.with(teamIcon).load(currentTeamShowing.getTeamLogo()).into(teamIcon);
        backButton.setOnClickListener( v -> onBackPressed());
        ViewCompat.setElevation(mainAppBar, 8);
        fetchCompleteDetailOfTeam(currentTeamShowing);

        mainContentRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void fetchCompleteDetailOfTeam(Team team) {
        if (team == null) return;

        App.getInstance().getApi().getTeamDetail(team, completeTeam -> {
            currentTeamShowing = completeTeam;
            setupViews();
        } , error -> Log.e("Team_Info" , error));
    }

    private void setupViews() {
        mainContentRecyclerView.setAdapter(new TeamInformationAdapter(currentTeamShowing));
    }

}
