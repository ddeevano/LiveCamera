package com.livecamera.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Location implements Serializable {

    // Attributes..
    private String city;
    private String region;
    private String regionCode;
    private String country;
    private String latitude;
    private String longitude;
    private String timezone;

    public Location(JSONObject location) throws JSONException {

        this.city = location.getString("city");
        this.region = location.getString("region");
        this.regionCode = location.getString("region_code");
        this.country = location.getString("country");
        this.latitude = location.getString("latitude");
        this.longitude = location.getString("longitude");
        this.timezone = location.getString("timezone");

    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public String getCountry() {
        return country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

}
