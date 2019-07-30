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
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.yenepaySDK.PaymentOrderManager;
import com.yenepaySDK.model.YenePayConfiguration;

import app.kuwas.android.ui.activities.AboutActivity;
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
