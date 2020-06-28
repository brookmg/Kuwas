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

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.ui.activities.MainActivity;
import app.kuwas.android.ui.adapters.OnItemActionListener;
import app.kuwas.android.ui.adapters.TopPlayersRecyclerAdapter;
import app.kuwas.android.utils.FabStates;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.round;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public class TopPlayersFragment extends BaseFragment {

    private RecyclerView mainRecycler;
    private Integer recyclerViewY = 0;
    private RelativeLayout contentLoadingIndicator;
    private View errorLayout;
    private boolean alreadyLoadedCachedContent = false;

    static TopPlayersFragment newInstance() {
        Bundle args = new Bundle();
        TopPlayersFragment fragment = new TopPlayersFragment();
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

    private void changeErrorVisibility(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        errorLayout.animate().alpha(show ? 1 : 0).setDuration(500).start();
    }

    @Override
    public void refresh() {
        super.refresh();
        showLoadingLayout();
        if (mainRecycler != null)
            App.getInstance().getApiBridge().getTopPlayers(
                    players -> {
                        if (players != null) alreadyLoadedCachedContent = true;
                        Log.d("players", Arrays.toString(players.toArray()));
                        mainRecycler.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.VERTICAL, false));
                        mainRecycler.setAdapter(new TopPlayersRecyclerAdapter(mainRecycler, players, new OnItemActionListener() {
                            @Override
                            public void onItemClicked(View view, int position) {

                            }

                            @Override
                            public boolean onItemLongClicked(View view, int position) {
                                return false;
                            }
                        }) );
                        hideLoadingLayout();
                        changeErrorVisibility(false);
                    },
                    error -> {
                        Log.e("TopPFragment" , error);
                        hideLoadingLayout();
                        if (!alreadyLoadedCachedContent) changeErrorVisibility(true);
                    }
            );
    }

    private void computeRecyclerViewYForAppbarElevation(Integer yDiff) {
        recyclerViewY += yDiff;
        setAppBarElevation(round(min(recyclerViewY * 0.4f, 19f)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.scores_fragment, container, false);
        mainRecycler = mainView.findViewById(R.id.mainScoresRecyclerView);
        contentLoadingIndicator = mainView.findViewById(R.id.loading_layout);
        errorLayout = mainView.findViewById(R.id.error_layout);

        refresh();
        mainView.findViewById(R.id.refresh_button).setOnClickListener(v -> refresh());

        mainRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (getActivity() instanceof MainActivity) {
                    computeRecyclerViewYForAppbarElevation(dy);
                    if (abs(dy) > 10) {
                        if (dy < 0)
                            ((MainActivity) getActivity()).changeFabState(FabStates.STATE_SHOW);    //scrolling upward so expand fab
                        else if (dy > 0)
                            ((MainActivity) getActivity()).changeFabState(FabStates.STATE_HIDE); //scrolling downward so shrink fab
                    }
                }
            }
        });

        return mainView;
    }
}
