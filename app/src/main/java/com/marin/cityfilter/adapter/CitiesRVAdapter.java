package com.marin.cityfilter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marin.cityfilter.model.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Marin Kacaj
 */
public class CitiesRVAdapter extends RecyclerView.Adapter<CitiesRVAdapter.CityVH> {

    private final List<City> cities = new ArrayList<>();

    public void none() {
        this.cities.clear();
        this.notifyDataSetChanged();
    }

    public void cities(List<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return CityVH.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CityVH holder, int position) {
        City city = cities.get(position);
        holder.bind(city);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    static final class CityVH extends RecyclerView.ViewHolder {

        private final TextView fullNameView;

        CityVH(View itemView) {
            super(itemView);
            this.fullNameView = itemView.findViewById(android.R.id.text1);
        }

        static CityVH create(ViewGroup parent) {
            View itemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new CityVH(itemView);
        }

        void bind(City city) {
            String fullName = String.format(Locale.ENGLISH, "%s, %s", city.getName(), city.getCountry());
            this.fullNameView.setText(fullName);
        }
    }
}
