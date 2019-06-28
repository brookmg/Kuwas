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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import app.kuwas.android.ui.adapters.FavoriteRecyclerAdapter;
import app.kuwas.android.ui.adapters.TabAdapter;
import app.kuwas.android.ui.adapters.TagsChipRecyclerAdapter;
import app.kuwas.android.utils.FabStates;
import io.brookmg.soccerethiopiaapi.data.Team;
import io.brookmg.soccerethiopiaapi.utils.Constants;
import app.kuwas.android.R;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.round;

public class FavoriteTeam extends AppCompatActivity {

    RecyclerView mainFavoriteRecyclerView, selectedTeams;
    AppBarLayout appBarLayout;
    ExtendedFloatingActionButton continueButton;
    FavoriteRecyclerAdapter mainAdapter;
    List<String> selectedTeamNames = new ArrayList<>();
    TagsChipRecyclerAdapter mainTagsAdapter = new TagsChipRecyclerAdapter(selectedTeamNames);
    int recyclerViewY = 0;
    AppCompatTextView nothingSelected;

    private void computeRecyclerViewScrollForAppbarElevation(Integer yDiff) {
        recyclerViewY += yDiff; //not reliable, but it's one way to find scroll position to compute the elevation for the elevation
        setAppBarElevation(round(min(recyclerViewY * 0.8f, 19f)));
    }

    private void setAppBarElevation(float value) {
        ViewCompat.setElevation(appBarLayout, value);
    }

    private void changeFabState(@FabStates.FabState int fabstate) {
        switch (fabstate) {
            case FabStates.STATE_COLLAPSE:
                continueButton.shrink(true);
                break;
            case FabStates.STATE_EXPAND:
                continueButton.extend(true);
                break;
            case FabStates.STATE_HIDE:
                continueButton.hide(true);
                break;
            case FabStates.STATE_SHOW:
                continueButton.show(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_team);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white_0y));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );
        }

        appBarLayout = findViewById(R.id.appbar_layout);
        nothingSelected = findViewById(R.id.nothing_selected);
        mainFavoriteRecyclerView = findViewById(R.id.main_favorite_recycler_view);
        selectedTeams = findViewById(R.id.selected_teams);
        continueButton = findViewById(R.id.continue_button);
        mainAdapter = new FavoriteRecyclerAdapter(Constants.teams, selected -> {
            selectedTeamNames.clear();

            for (Team team : selected) selectedTeamNames.add(team.getTeamFullName());

            mainTagsAdapter = new TagsChipRecyclerAdapter(selectedTeamNames);
            selectedTeams.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            selectedTeams.setAdapter(mainTagsAdapter);

            nothingSelected.setVisibility(selected.isEmpty() ? View.VISIBLE : View.GONE);
            selectedTeams.setVisibility(!selected.isEmpty() ? View.VISIBLE : View.GONE);
            continueButton.setEnabled(!selected.isEmpty());

        });

        mainFavoriteRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mainFavoriteRecyclerView.setAdapter(mainAdapter);
        selectedTeams.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        selectedTeams.setAdapter(mainTagsAdapter);

        continueButton.setOnClickListener(view -> {
            // get selected items from the adapter
            
        });

        mainFavoriteRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                computeRecyclerViewScrollForAppbarElevation(dy);
                if (abs(dy) > 10)
                    if (dy < 0) changeFabState(FabStates.STATE_EXPAND);    //scrolling upward so expand fab
                    else if (dy > 0) changeFabState(FabStates.STATE_COLLAPSE); //scrolling downward so shrink fab
            }
        });
    }


}
