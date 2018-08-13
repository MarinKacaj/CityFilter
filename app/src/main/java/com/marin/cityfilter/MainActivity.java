package com.marin.cityfilter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.TextView;

import com.marin.cityfilter.adapter.CitiesRVAdapter;
import com.marin.cityfilter.loader.CitiesLoader;
import com.marin.cityfilter.model.City;

import java.util.List;

import static com.marin.cityfilter.ViewUtil.gone;
import static com.marin.cityfilter.ViewUtil.visible;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<List<City>>,
        SearchView.OnQueryTextListener {

    private static final int LOADER_ID = 0;
    private TextView messageView;
    private SearchView searchBar;
    private CitiesRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageView = findViewById(R.id.message);
        searchBar = findViewById(R.id.search_bar);
        searchBar.setOnQueryTextListener(this);
        adapter = new CitiesRVAdapter();

        RecyclerView recyclerView = findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private CityFilterApp cityFilterApp() {
        return (CityFilterApp) getApplication();
    }

    @Override
    public Loader<List<City>> onCreateLoader(int i, Bundle bundle) {
        return new CitiesLoader(this, cityFilterApp().getGson());
    }

    @Override
    public void onLoadFinished(Loader<List<City>> loader, List<City> cities) {
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
    public void onLoaderReset(Loader<List<City>> loader) {
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true; // query performed here
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return true; // no search-bar suggestions required
    }
}
