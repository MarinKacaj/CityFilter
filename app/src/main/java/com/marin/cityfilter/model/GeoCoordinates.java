package com.marin.cityfilter.model;

/**
 * @author Marin Kacaj
 */
public class GeoCoordinates {
    private double lon;
    private double lat;

    // for gson reflection instantiation
    public GeoCoordinates() {
    }

    public GeoCoordinates(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
