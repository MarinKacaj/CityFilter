package com.marin.cityfilter;

import android.content.Intent;
import android.net.Uri;

import com.marin.cityfilter.model.City;

import java.util.Locale;

/**
 * @author Marin Kacaj
 */
public class GMapUtil {

    public static Intent gMapIntent(City city) {
        double lat = city.getCoord().getLat();
        double lng = city.getCoord().getLon();
        String mapsUri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
        Uri gmmIntentUri = Uri.parse(mapsUri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        return mapIntent;
    }
}
