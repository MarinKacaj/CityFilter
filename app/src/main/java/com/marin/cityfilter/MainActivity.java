package com.marin.cityfilter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.marin.cityfilter.adapter.CitiesAdapter;
import com.marin.cityfilter.loader.CitiesLoader;
import com.marin.cityfilter.model.City;

import java.util.Collection;

import static com.marin.cityfilter.ViewUtil.gone;
import static com.marin.cityfilter.ViewUtil.visible;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Collection<City>>,
        SearchView.OnQueryTextListener {

    private static final int CITIES_LOADER_ID = 0;
    private TextView messageView;
    private SearchView searchBar;
    private CitiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageView = findViewById(R.id.message);
        searchBar = findViewById(R.id.search_bar);
        searchBar.setOnQueryTextListener(this);
        adapter = new CitiesAdapter();

        final ListView cityList = findViewById(R.id.list);
        cityList.setAdapter(adapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = adapter.getItem(position);
                Intent mapIntent = GMapUtil.gMapIntent(city);
                PackageManager packageManager = getPackageManager();
                if (mapIntent.resolveActivity(packageManager) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.no_map_app, Toast.LENGTH_LONG).show();
                }
            }
        });
        loadCities("");
    }

    private void loadCities(String prefix) {
        CitiesLoader.start(CITIES_LOADER_ID, prefix, this, getLoaderManager());
    }

    private CityFilterApp cityFilterApp() {
        return (CityFilterApp) getApplication();
    }

    @Override
    public Loader<Collection<City>> onCreateLoader(int id, Bundle args) {
        if (CITIES_LOADER_ID == id) {
            return CitiesLoader.obtain(this, args, cityFilterApp().getGson());
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Collection<City>> loader, Collection<City> cities) {
        if (null == cities) {
            visible(messageView);
            gone(searchBar);
            messageView.setText(R.string.error);
            adapter.none();
        } else {
            gone(messageView);
            visible(searchBar);
            adapter.cities(cities);
        }
    }

    @Override
    public void onLoaderReset(Loader<Collection<City>> loader) {
        adapter.none();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        loadCities(query);
        return true; // query performed here
    }

    @Override
    public boolean onQueryTextChange(String query) {
        loadCities(query);
        return true; // no search-bar suggestions required
    }

}
