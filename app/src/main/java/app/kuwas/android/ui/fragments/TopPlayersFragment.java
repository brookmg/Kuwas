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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.ui.activities.MainActivity;
import app.kuwas.android.ui.adapters.ScoresRecyclerAdapter;
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

    static TopPlayersFragment newInstance() {
        Bundle args = new Bundle();
        TopPlayersFragment fragment = new TopPlayersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void refresh() {
        super.refresh();
        if (mainRecycler != null)
            App.getInstance().getApi().getTopPlayers(
                    players -> {
                        Log.d("players", Arrays.toString(players.toArray()));
                        mainRecycler.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.VERTICAL, false));
                        mainRecycler.setAdapter(new TopPlayersRecyclerAdapter(players));
                    },
                    error -> Log.e("TopPFragment" , error)
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

        refresh();

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
