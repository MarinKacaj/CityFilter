package com.marin.cityfilter.loader;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marin.cityfilter.model.City;
import com.marin.cityfilter.prefixtrie.CityPrefixTrie;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * An "emulated" simple content provider.
 * An actual {@link android.content.ContentProvider content provider} not necessary, as we only read
 *
 * @author Marin Kacaj
 */
public class CitiesProvider {

    private static CityPrefixTrie cityPrefixTrie;

    public static CityPrefixTrie cityPrefixTrie(Context context, Gson gson) throws IOException {
        if (null == cityPrefixTrie) {
            InputStream citiesStream = context.getAssets().open("cities.json");
            Reader reader = new InputStreamReader(citiesStream, "UTF-8");
            Type listType = new TypeToken<List<City>>() {
            }.getType();
            List<City> cities = gson.fromJson(reader, listType);
            Collections.sort(cities);
            cityPrefixTrie = new CityPrefixTrie();
            for (City city : cities) {
                cityPrefixTrie.put(city.getName().toLowerCase(), city);
            }
        }
        return cityPrefixTrie;
    }
}
