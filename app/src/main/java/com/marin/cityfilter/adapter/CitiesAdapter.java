package com.marin.cityfilter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marin.cityfilter.model.City;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Marin Kacaj
 */
public class CitiesAdapter extends BaseAdapter {

    private final List<City> cities = new ArrayList<>();

    public void none() {
        this.cities.clear();
        this.notifyDataSetChanged();
    }

    public void cities(Collection<City> cities) {
        this.cities.clear();
        this.cities.addAll(cities);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public City getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int i) {
        return cities.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityVH viewHolder;

        if (null == convertView) {
            convertView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new CityVH(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CityVH) convertView.getTag();
        }

        City city = getItem(position);
        viewHolder.bind(city);

        return convertView;
    }

    static final class CityVH {

        private final TextView fullNameView;

        CityVH(View itemView) {
            this.fullNameView = itemView.findViewById(android.R.id.text1);
        }

        void bind(City city) {
            this.fullNameView.setText(city.getFullName());
        }
    }
}
