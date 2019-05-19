package app.kuwas.android.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.ui.widgets.StandingTable;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public class StandingFragment extends BaseFragment {

    private StandingTable mainTable;

    static StandingFragment newInstance() {
        Bundle args = new Bundle();
        StandingFragment fragment = new StandingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void refresh() {
        super.refresh();
        if (mainTable != null)
            App.getInstance().getApi().getLatestTeamRanking(
                    ranking -> {
                        mainTable.populateTable(ranking);
                        mainTable.invalidate();
                    }, error -> Log.e("Ranking" , error)
            );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.standing_fragment, container, false);
        mainTable = mainView.findViewById(R.id.main_standing_table);
        return mainView;
    }
}
