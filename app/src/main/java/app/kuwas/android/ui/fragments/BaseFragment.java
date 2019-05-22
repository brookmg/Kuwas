package app.kuwas.android.ui.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import app.kuwas.android.ui.activities.MainActivity;

import static app.kuwas.android.utils.FabStates.FabState;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public abstract class BaseFragment extends Fragment {

    public void changeFabState (@FabState int state) {}

    public void refresh () {}

    final void runOnUiThread(Runnable run) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(run);
        }
    }

    final void changeFragment(String tag, Bundle bundle) {
        if (getActivity() != null) ((MainActivity) getActivity()).changeFragment(tag, bundle, null);
    }

    final void changeFragment(String tag, Bundle bundle, View view) {
        if (getActivity() != null) ((MainActivity) getActivity()).changeFragment(tag, bundle, view);
    }

}
