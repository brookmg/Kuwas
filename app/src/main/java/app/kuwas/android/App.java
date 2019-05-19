package app.kuwas.android;

import android.app.Application;

import io.brookmg.soccerethiopiaapi.access.SoccerEthiopiaApi;

/**
 * Created by BrookMG on 5/18/2019 in app.kuwas.android
 * inside the project Kuwas .
 */
public class App extends Application {

    private static App instance;
    private SoccerEthiopiaApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        api = new SoccerEthiopiaApi(this, true);
    }

    public static App getInstance() {
        return instance;
    }

    public SoccerEthiopiaApi getApi() {
        return api;
    }
}
