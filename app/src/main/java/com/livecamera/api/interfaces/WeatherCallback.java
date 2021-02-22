package com.livecamera.api.interfaces;

import com.livecamera.api.Weather;

public interface WeatherCallback {

    public abstract void onWeatherResponse(Weather weather);
    public abstract void onFailed();

}
