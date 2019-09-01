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

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

import app.kuwas.android.R;
import app.kuwas.android.utils.Constants;
import app.kuwas.android.utils.Utils;

import static android.view.View.GONE;
import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.VISIBLE;
import static app.kuwas.android.utils.Utils.pxToDp;
import static app.kuwas.android.utils.Utils.setCurrentTheme;

public class Splash extends AppCompatActivity {

    LinearLayout revealable;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Utils.getCurrentTheme(this) == 0 ? R.style.KuwasLightTheme : R.style.KuwasDarkTheme);
        setContentView(R.layout.activity_splash);

        revealable = findViewById(R.id.revealable);
        lottieAnimationView = findViewById(R.id.loading_animation_view);

        lottieAnimationView.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(
                        ContextCompat.getColor(
                                Splash.this ,
                            Utils.getCurrentTheme(Splash.this) == 0 ? R.color.black_0 : R.color.colorPrimaryDark
                        ), PorterDuff.Mode.SRC_ATOP
                )
        );

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, android.R.color.transparent));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | (Utils.getCurrentTheme(this) == 0 ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0)
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE | (Utils.getCurrentTheme(this) == 0 ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0));
        }
        _computeAndReveal(revealable);
    }

    private void _computeAndReveal(View revealView) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, displayMetrics.widthPixels * 2);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            ConstraintLayout.LayoutParams layoutParams = ((ConstraintLayout.LayoutParams) revealView.getLayoutParams());
            layoutParams.width = pxToDp(Splash.this, ((Integer) animation.getAnimatedValue()));
            revealView.setLayoutParams(layoutParams);
        });

        final boolean hasBestFavorites;
        hasBestFavorites = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(Constants.PREFERENCE_FAV_TEAMS, "[]") != null &&
                !PreferenceManager.getDefaultSharedPreferences(this)
                        .getString(Constants.PREFERENCE_FAV_TEAMS, "[]")
                        .equals("[]");


        new Handler().postDelayed(() -> {
            valueAnimator.setDuration(1_000);
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    //revealable.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //revealable.setVisibility(VISIBLE);
                    Intent noReturn = new Intent(Splash.this, hasBestFavorites ? MainActivity.class : FavoriteTeam.class);
                    noReturn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(noReturn);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.stay_still);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            valueAnimator.start();
        }, 2_500);
    }

}
