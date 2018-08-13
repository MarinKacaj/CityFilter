package com.marin.cityfilter.model;

import java.util.Locale;

/**
 * @author Marin Kacaj
 */
public class City implements Comparable<City> {

    private long _id;
    private String country;
    private String name;
    private GeoCoordinates coord;

    private transient String fullName;

    public long getId() {
        return _id;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        if (null == fullName) {
            fullName = String.format(Locale.ENGLISH, "%s, %s", getName(), getCountry());
        }
        return fullName;
    }

    public GeoCoordinates getCoord() {
        return coord;
    }

    @Override
    public int compareTo(City city) {
        // will throw NPE, per Comparable#compareTo java docs
        return getFullName().compareToIgnoreCase(city.getFullName());
    }
}
