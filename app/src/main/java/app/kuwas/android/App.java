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

package app.kuwas.android;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.yenepaySDK.PaymentOrderManager;
import com.yenepaySDK.model.YenePayConfiguration;

import app.kuwas.android.ui.activities.AboutActivity;
import app.kuwas.android.utils.Utils;
import io.brookmg.soccerethiopiaapi.access.SoccerEthiopiaApi;

/**
 * Created by BrookMG on 5/18/2019 in app.kuwas.android
 * inside the project Kuwas .
 */
public class App extends Application {

    private static App instance;
    private SoccerEthiopiaApi api;
    private FirebaseRemoteConfig remoteConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.fetchAndActivate().addOnCompleteListener(
                task -> Log.d("REMOTE_CONFIG", "completed - " +
                        (task.isComplete() && task.isSuccessful() ? "YAP" : "NOP"))
        );

        api = new SoccerEthiopiaApi(this, true);

        PendingIntent completionIntent = PendingIntent.getActivity(this, PaymentOrderManager.YENEPAY_CHECKOUT_REQ_CODE, new Intent(this, AboutActivity.class), 0);
        PendingIntent cancelIntent = PendingIntent.getActivity(this, PaymentOrderManager.YENEPAY_CHECKOUT_REQ_CODE, new Intent(this, AboutActivity.class), 0);

        YenePayConfiguration.setDefaultInstance(
                new YenePayConfiguration.Builder(this)
                        .setGlobalCompletionIntent(completionIntent)
                        .setGlobalCancelIntent(cancelIntent)
                        .build());

        // system wide dark mode?
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                Utils.setCurrentTheme(this, 1);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                Utils.setCurrentTheme(this, 0);
                break;
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                Utils.setCurrentTheme(this, 1);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                Utils.setCurrentTheme(this , 0);
                break;
        }
    }

    public static App getInstance() {
        return instance;
    }

    public SoccerEthiopiaApi getApi() {
        return api;
    }

    public FirebaseRemoteConfig getRemoteConfig() {
        return remoteConfig;
    }
}
