package com.marin.cityfilter.loader;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.google.gson.Gson;
import com.marin.cityfilter.model.City;
import com.marin.cityfilter.prefixtrie.CityPrefixTrie;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Marin Kacaj
 */
public class CitiesLoader extends AsyncTaskLoader<Collection<City>> {

    private static final String KEY_PREFIX = "city.name.prefix";

    private Gson gson;
    private final String prefix;

    public CitiesLoader(Context context, Gson gson, String prefix) {
        super(context);
        this.gson = gson;
        this.prefix = prefix;
        forceLoad();
    }

    public static CitiesLoader obtain(Context context, Bundle args, Gson gson) {
        String prefix = args.getString(KEY_PREFIX);
        return new CitiesLoader(context, gson, prefix);
    }

    public static void start(int loaderId, String prefix,
                             LoaderManager.LoaderCallbacks<Collection<City>> callbacks,
                             LoaderManager loaderManager) {
        Bundle args = new Bundle();
        args.putString(KEY_PREFIX, prefix);
        Loader<Collection<City>> loader = loaderManager.getLoader(loaderId);
        if (loader != null && !loader.isReset()) {
            loaderManager.restartLoader(loaderId, args, callbacks);
        } else {
            loaderManager.initLoader(loaderId, args, callbacks);
        }
    }

    @Override
    public Collection<City> loadInBackground() {
        Collection<City> filteredSortedCities;
        try {
            CityPrefixTrie cityPrefixTrie = CitiesProvider.cityPrefixTrie(getContext(), gson);
            filteredSortedCities = cityPrefixTrie.getValuesWithPrefix(prefix);
        } catch (IOException e) {
            filteredSortedCities = null;
        }
        return filteredSortedCities;
    }
}
