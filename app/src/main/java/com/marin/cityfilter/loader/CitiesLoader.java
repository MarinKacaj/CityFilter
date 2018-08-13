package com.marin.cityfilter.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marin.cityfilter.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Marin Kacaj
 */
public class CitiesLoader extends AsyncTaskLoader<List<City>> {

    private Gson gson;

    public CitiesLoader(Context context, Gson gson) {
        super(context);
        this.gson = gson;
        forceLoad();
    }

    @Override
    public List<City> loadInBackground() {
        List<City> cities;
        try {
            InputStream citiesStream = getContext().getAssets().open("cities.json");
            Reader reader = new InputStreamReader(citiesStream, "UTF-8");
            Type listType = new TypeToken<List<City>>() {
            }.getType();
            cities = gson.fromJson(reader, listType);
        } catch (IOException e) {
            cities = null;
        }
        return cities;
    }
}
