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
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

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
