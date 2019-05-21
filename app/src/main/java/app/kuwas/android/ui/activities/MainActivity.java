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

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;

import app.kuwas.android.R;
import app.kuwas.android.ui.fragments.BaseFragment;
import app.kuwas.android.ui.fragments.HomeFragment;
import app.kuwas.android.ui.fragments.NewsPreviewFragment;
import app.kuwas.android.utils.FragmentHelper;

import static app.kuwas.android.utils.Constants.TAG_HOME;
import static app.kuwas.android.utils.Constants.TAG_NEWS_PREVIEW;
import static app.kuwas.android.utils.FabStates.FabState;

public class MainActivity extends AppCompatActivity {

    private FrameLayout _fragmentContainer;
    private FragmentHelper fragmentHelper;
    private WeakReference<Fragment> currentFragment;

    private void setCurrentFragment(BaseFragment fragment) {
        currentFragment = new WeakReference<>(fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _fragmentContainer = findViewById(R.id.fragmentContainer);

        fragmentHelper = new FragmentHelper();
        changeFragment(TAG_HOME, new Bundle());

    }

    public void changeFabState(@FabState int state) {
        if (currentFragment != null && currentFragment.get() != null && currentFragment.get() instanceof BaseFragment)
            ((BaseFragment) currentFragment.get()).changeFabState(state);
    }

    private String getFragmentTag(BaseFragment fragment) {
        if (fragment instanceof HomeFragment) return TAG_HOME;
        else if (fragment instanceof NewsPreviewFragment) return TAG_NEWS_PREVIEW;
        else return TAG_HOME;
    }

    private void goToPreviousFragment() {
        BaseFragment lastFragment = fragmentHelper.getItemFromQueue(fragmentHelper.queueSize() -2);
        goBackFrag(getFragmentTag(lastFragment) , lastFragment, lastFragment.getArguments());
        setCurrentFragment(lastFragment);
        fragmentHelper.removeLastFragment();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment != null && currentFragment.get() != null) {
            //This is some fragment that has loaded
            if (currentFragment.get() instanceof HomeFragment) {
                Snackbar.make(_fragmentContainer, "sure you want to exit?" , Snackbar.LENGTH_SHORT).setAction("Yap!" , (v) -> finish()).show();
            } else {
                goToPreviousFragment();
            }
        } else {
            super.onBackPressed();
        }
    }

    public void changeFragment (String fragmentTag , Bundle bundle) {
        switch (fragmentTag) {
            case TAG_HOME: {
                BaseFragment baseFragment = HomeFragment.newInstance();
                fragStarter(fragmentTag , baseFragment , bundle);
                break;
            }

            case TAG_NEWS_PREVIEW: {
                BaseFragment baseFragment = NewsPreviewFragment.newInstance(bundle);
                fragStarter(fragmentTag , baseFragment, bundle);
            }
        }
    }

    private void fragStarter (String fragTag , BaseFragment baseFragment , Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer , baseFragment , fragTag);
        fragmentTransaction.addToBackStack(fragTag);
        fragmentTransaction.commit();

        fragmentHelper.addFragmentToQueue(baseFragment);
        setCurrentFragment(baseFragment);
    }

    private void goBackFrag (String fragTag , BaseFragment baseFragment , Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer , baseFragment , fragTag);
        fragmentTransaction.addToBackStack(fragTag);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
