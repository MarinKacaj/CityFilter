package com.marin.cityfilter.model;

/**
 * @author Marin Kacaj
 */
public class City {

    private long _id;
    private String country;
    private String name;
    private GeoCoordinates coord;

    public long get_id() {
        return _id;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public GeoCoordinates getCoord() {
        return coord;
    }
}
