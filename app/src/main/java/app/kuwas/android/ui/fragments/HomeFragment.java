package app.kuwas.android.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import app.kuwas.android.R;
import app.kuwas.android.ui.adapters.TabAdapter;
import app.kuwas.android.utils.FabStates;

import static app.kuwas.android.utils.FabStates.STATE_EXPAND;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment extends BaseFragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.home_fragment, container, false);
        appBarLayout = mainView.findViewById(R.id.appbar_layout);
        tabLayout = mainView.findViewById(R.id.tab_layout);
        viewPager = mainView.findViewById(R.id.main_view_pager);
        refreshFab = mainView.findViewById(R.id.refresh_fab);

        if (getActivity() != null) {
            tabAdapter = new TabAdapter(getChildFragmentManager());
            tabAdapter.addFragment(NewsFragment.newInstance(), "News");
            tabAdapter.addFragment(ScoresFragment.newInstance(), "Scores");
            tabAdapter.addFragment(StandingFragment.newInstance(), "Standing");

            viewPager.setAdapter(tabAdapter);
            viewPager.setOffscreenPageLimit(3);
            tabLayout.setupWithViewPager(viewPager);
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
