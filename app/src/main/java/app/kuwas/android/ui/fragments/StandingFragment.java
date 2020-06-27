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

package app.kuwas.android.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.ui.activities.TeamInformation;
import app.kuwas.android.ui.widgets.StandingTable;

import static java.lang.Math.min;
import static java.lang.Math.round;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public class StandingFragment extends BaseFragment {

    private StandingTable mainTable;
    private RelativeLayout contentLoadingIndicator;
    private View errorLayout;
    private boolean alreadyLoadedCachedContent = false;

    static StandingFragment newInstance() {
        Bundle args = new Bundle();
        StandingFragment fragment = new StandingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void showLoadingLayout() {
        if (contentLoadingIndicator != null) {
            contentLoadingIndicator.animate().alpha(1f).setDuration(500).start();
        }
    }

    private void hideLoadingLayout() {
        if (contentLoadingIndicator != null) {
            contentLoadingIndicator.animate().alpha(0f).setDuration(500).start();
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        showLoadingLayout();
        if (mainTable != null)
            App.getInstance().getApiBridge().getLatestTeamRanking(
                    ranking -> {
                        if (ranking != null) alreadyLoadedCachedContent = true;
                        mainTable.clearTable();
                        mainTable.populateTable(ranking);
                        mainTable.invalidate();
                        hideLoadingLayout();
                        changeErrorVisibility(false);
                    }, error -> {
                        Log.e("Ranking" , error);
                        hideLoadingLayout();
                        if (!alreadyLoadedCachedContent) changeErrorVisibility(true);
                    }
                    , true
            );
    }

    private void changeErrorVisibility(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        errorLayout.animate().alpha(show ? 1 : 0).setDuration(500).start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.standing_fragment, container, false);
        mainTable = mainView.findViewById(R.id.main_standing_table);
        contentLoadingIndicator = mainView.findViewById(R.id.loading_layout);
        errorLayout = mainView.findViewById(R.id.error_layout);

        refresh();
        mainView.findViewById(R.id.refresh_button).setOnClickListener(v -> refresh());
        ((NestedScrollView) mainView.findViewById(R.id.nested_scroll_view)).setOnScrollChangeListener(
                (NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
                        setAppBarElevation(round(min(scrollY * 0.4f , 12f)))
        );

        mainTable.setOnTableRowClickedCallback(team -> {
            Intent intent = new Intent(getActivity(), TeamInformation.class);
            intent.putExtra("team", team);
            startActivity(intent);
        });
        return mainView;
    }
}
