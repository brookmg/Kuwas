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
import android.view.View;
import androidx.fragment.app.Fragment;

import app.kuwas.android.ui.activities.MainActivity;

import static app.kuwas.android.utils.FabStates.FabState;

/**
 * Created by BrookMG on 4/9/2019 in app.kuwas.android.ui.fragments
 * inside the project Kuwas .
 */
public abstract class BaseFragment extends Fragment {

    private int appBarElevation = 0;

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

    final void setAppBarElevation(Integer elevation) {
        appBarElevation = elevation;
        if (getActivity() != null) ((MainActivity) getActivity()).setAppBarElevation(elevation);
    }

    final int getAppBarElevation() {
        return appBarElevation;
    }
}
