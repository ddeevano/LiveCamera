package com.livecamera.api;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    // Attributes..
    private double centigrade;
    private double fahrenheit;
    private double wind_mph;
    private String windDirection;
    private String condition;
    private double humidity;
    private double feelsLikeC;
    private double feelsLikeF;
    private double visibility;

    public Weather(JSONObject object) throws JSONException {

        this.centigrade = object.getDouble("temp_c");
        this.fahrenheit = object.getDouble("temp_f");
        this.wind_mph = object.getDouble("wind_mph");
        this.windDirection = object.getString("wind_dir");
        this.condition = object.getJSONObject("condition").getString("text");
        this.humidity = object.getDouble("humidity");
        this.feelsLikeC = object.getDouble("feelslike_c");
        this.feelsLikeF = object.getDouble("feelslike_f");
        this.visibility = object.getDouble("vis_miles");

    }

    public double getCentigrade() {
        return centigrade;
    }

    public double getFahrenheit() {
        return fahrenheit;
    }

    public double getWind_mph() {
        return wind_mph;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getFeelsLikeC() {
        return feelsLikeC;
    }

    public double getFeelsLikeF() {
        return feelsLikeF;
    }

    public double getVisibility() {
        return visibility;
    }

    public String getCondition() {
        return condition;
    }

}
