/*
 * Copyright (C) 2019 Brook Mezgebu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.kuwas.soccerethiopia.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by BrookMG on 5/13/2019 in io.brookmg.soccerethiopiaapi.utils
 * inside the project SoccerEthiopia .
 */
public class ThreadPoolProvider {

    private static ThreadPoolProvider instance;
    private ExecutorService mainExecutorService;

    private ThreadPoolProvider(int maxTreadPullSize) {
        mainExecutorService = Executors.newFixedThreadPool(maxTreadPullSize);
        instance = this;
    }

    private ThreadPoolProvider() {
        mainExecutorService = Executors.newCachedThreadPool();
        instance = this;
    }

    public static ThreadPoolProvider getInstance() {
        if (instance != null) return instance;
        else return new ThreadPoolProvider();
    }

    public static ThreadPoolProvider getInstance(int maxThreadPullSize) {
        if (instance != null) return instance;
        else return new ThreadPoolProvider(maxThreadPullSize);
    }


    public void execute(Runnable task) {
        if (mainExecutorService != null) mainExecutorService.execute(task);
    }

//    public Future execute(Callable task) {
//        return mainExecutorService.submit(task);
//    }

    public void executeOnMainThread(Runnable task) {
        new Handler(Looper.getMainLooper()).post(task);
    }

    public void executeOnMainThreadDelayed(Runnable task, long delay) {
        new Handler(Looper.getMainLooper()).postDelayed(task, delay);
    }
}
