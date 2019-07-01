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

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import app.kuwas.android.R;
import app.kuwas.android.ui.activities.MainActivity;
import app.kuwas.android.ui.adapters.TabAdapter;
import app.kuwas.android.utils.FabStates;

import static app.kuwas.android.utils.FabStates.STATE_EXPAND;
import static app.kuwas.android.utils.Utils.dpToPx;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment extends BaseFragment {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ExtendedFloatingActionButton refreshFab;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setElevationLevel(Integer elevationLevel) {
        ViewCompat.setElevation(appBarLayout, elevationLevel);
    }

    // TODO: 7/1/2019 FIGURE OUT A WAY TO ADDRESS POPUP AND MULTI-SCREEN CASES
    private void handleTopPaddingOnAppBarLayout(AppBarLayout appBarLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // the sdk is greater than lollipop; the app is being drawn under the status bar
            appBarLayout.setPadding(0, getActivity() != null ? dpToPx(getActivity(), 24) : 0, 0, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.home_fragment, container, false);

        toolbar = mainView.findViewById(R.id.toolbar);
        appBarLayout = mainView.findViewById(R.id.appbar_layout);
        tabLayout = mainView.findViewById(R.id.tab_layout);
        viewPager = mainView.findViewById(R.id.main_view_pager);
        refreshFab = mainView.findViewById(R.id.refresh_fab);

        handleTopPaddingOnAppBarLayout(appBarLayout);

        if (getActivity() != null) {
            tabAdapter = new TabAdapter(getChildFragmentManager());
            tabAdapter.addFragment(NewsFragment.newInstance(), "News");
            tabAdapter.addFragment(ScoresFragment.newInstance(), "Scores");
            tabAdapter.addFragment(StandingFragment.newInstance(), "Standing");
            tabAdapter.addFragment(TopPlayersFragment.newInstance(), "Top Players");

            viewPager.setAdapter(tabAdapter);
            viewPager.setOffscreenPageLimit(3);
            tabLayout.setupWithViewPager(viewPager);
            ((MainActivity) getActivity()).setSupportActionBar(toolbar);

            if (getActivity() instanceof  MainActivity && ((MainActivity) getActivity()).getSupportActionBar() != null)
                ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    setElevationLevel(((BaseFragment) tabAdapter.getmFragments()
                            .get(tab.getPosition())).getAppBarElevation());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        refreshFab.setOnClickListener(view -> refreshAllFragments(tabAdapter.getmFragments()));

        return mainView;
    }

    private void refreshAllFragments(List<Fragment> fragments) {
        for (Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) ((BaseFragment) fragment).refresh();
        }
    }

    @Override
    public void changeFabState(int state) {
        super.changeFabState(state);
        if (refreshFab != null) {
            if (state == STATE_EXPAND) refreshFab.extend(true);
            else if (state == FabStates.STATE_COLLAPSE) refreshFab.shrink(true);
            else if (state == FabStates.STATE_HIDE) refreshFab.hide(true);
            else if (state == FabStates.STATE_SHOW) refreshFab.show(true);
        }
    }
}
