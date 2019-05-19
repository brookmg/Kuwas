package app.kuwas.android.ui.fragments;

import androidx.fragment.app.Fragment;

import static app.kuwas.android.utils.FabStates.FabState;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public abstract class BaseFragment extends Fragment {

    public void changeFabState (@FabState int state) {}

}
