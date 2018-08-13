package com.marin.cityfilter;

import android.app.Application;

import com.google.gson.Gson;

/**
 * @author Marin Kacaj
 */
public class CityFilterApp extends Application {

    private Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();

        this.gson = new Gson();
    }

    public Gson getGson() {
        return gson;
    }
}
