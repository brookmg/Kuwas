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

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionInflater;

import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;

import app.kuwas.android.App;
import app.kuwas.android.R;
import app.kuwas.android.bridge.leagues.SoccerEthiopiaConnector;
import app.kuwas.android.ui.fragments.BaseFragment;
import app.kuwas.android.ui.fragments.HomeFragment;
import app.kuwas.android.ui.fragments.NewsPreviewFragment;
import app.kuwas.android.utils.Utils;

import static app.kuwas.android.utils.Constants.FRC_LATEST_VERSION;
import static app.kuwas.android.utils.Constants.TAG_HOME;
import static app.kuwas.android.utils.Constants.TAG_NEWS_PREVIEW;
import static app.kuwas.android.utils.FabStates.FabState;
import static app.kuwas.android.utils.Utils.getCurrentTheme;
import static app.kuwas.android.utils.Utils.openPlayStore;

public class MainActivity extends AppCompatActivity {

    private FrameLayout _fragmentContainer;
    private WeakReference<Fragment> currentFragment;

    private void setCurrentFragment(BaseFragment fragment) {
        currentFragment = new WeakReference<>(fragment);
    }

    public void setAppBarElevation(Integer elevation) {
        if (currentFragment.get() instanceof HomeFragment) {
            ((HomeFragment) currentFragment.get()).setElevationLevel(elevation);
        }
    }

    private boolean isThereUpdateAvailable() throws PackageManager.NameNotFoundException {
        long latestVersion = App.getInstance().getRemoteConfig().getLong(FRC_LATEST_VERSION);
        long currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        Log.d("UPDATE_STATUS", "available - " + (latestVersion > currentVersion ? "YAP" : "NOP"));
        return latestVersion > currentVersion;   // return true if an update is available.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Utils.getCurrentTheme(this) == 0 ? R.style.KuwasLightTheme : R.style.KuwasDarkTheme);
        setContentView(R.layout.activity_main);

        SoccerEthiopiaConnector soccerEthiopiaConnector = new SoccerEthiopiaConnector(
                this, true
        );
        soccerEthiopiaConnector.getLatestTeamRanking(item -> {
            item.size();
        }, e -> {});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (Utils.getCurrentTheme(this) == 0)
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );
            else
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );

        _fragmentContainer = findViewById(R.id.fragmentContainer);

        changeFragment(TAG_HOME, new Bundle(), null);
    }

    public void changeFabState(@FabState int state) {
        if (currentFragment != null && currentFragment.get() instanceof BaseFragment)
            ((BaseFragment) currentFragment.get()).changeFabState(state);
    }

    private void changeTheme() {
        int currentTheme = Utils.getCurrentTheme(this);
        if (currentTheme == 0) Utils.setCurrentTheme(this , 1);
        else Utils.setCurrentTheme(this, 0);
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            //there are more items in the back stack. We are not on the home frag
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            Snackbar.make(_fragmentContainer, "sure you want to exit?" , Snackbar.LENGTH_SHORT).setAction("Yap!" , (v) -> finish()).show();
        }

        if (getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1) instanceof BaseFragment)
        setCurrentFragment((BaseFragment) getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1));
    }

    public void changeFragment (String fragmentTag , Bundle bundle, @Nullable View view) {
        switch (fragmentTag) {
            case TAG_HOME: {
                BaseFragment baseFragment = HomeFragment.newInstance();
                fragStarter(fragmentTag , baseFragment , bundle, view);
                break;
            }

            case TAG_NEWS_PREVIEW: {
                BaseFragment baseFragment = NewsPreviewFragment.newInstance(bundle);

                if (Build.VERSION.SDK_INT >= 21) {
                    baseFragment.setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
                    baseFragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.no_transition));
                }

                fragStarter(fragmentTag , baseFragment, bundle, view);
            }
        }
    }

    private void fragStarter (String fragTag , BaseFragment baseFragment , Bundle bundle, View sharedView) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer , baseFragment , fragTag);
        if (sharedView != null){
            String transitionName = ViewCompat.getTransitionName(sharedView);
            fragmentTransaction.addSharedElement(sharedView , transitionName != null ? transitionName : "\\[_0_0_]/");
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setCurrentFragment(baseFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        try {
            menu.getItem(0).setVisible(isThereUpdateAvailable());   // make it only visible if there is an update available
            menu.getItem(1).setIcon(getCurrentTheme(this) == 0 ? R.drawable.ic_night_theme : R.drawable.ic_light_theme);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            openPlayStore(this);    // probably will change this to play core lib impl ... but not yet
            return true;
        } else if (id == R.id.action_theme) {
            changeTheme();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
