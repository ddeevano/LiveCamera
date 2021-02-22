package com.livecamera.api;

import android.util.Log;

import com.livecamera.api.interfaces.LiveCameraCallback;
import com.livecamera.api.interfaces.WeatherCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataFetcher {

    public void fetchCameraData(LiveCameraCallback cameraCallback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://webcamstravel.p.rapidapi.com/webcams/list/country=US/orderby%3Dpopularity%2Flimit%3D30%2C0%2Fproperty%3Dlive?lang=en&show=webcams%3Alocation%2Cplayer%2Cstatistics")
                .get()
                .addHeader("x-rapidapi-key", "fe346bd1b9msh9745031e0bd6cf2p1aebafjsnee5e067e8ffe")
                .addHeader("x-rapidapi-host", "webcamstravel.p.rapidapi.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                cameraCallback.onFailed();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    cameraCallback.onFailed();
                } else {
                    try {
                        ArrayList<LiveCamera> cameras = parseJSON(response.body().string());
                        cameraCallback.onLiveCameraResponse(cameras);
                    } catch (JSONException e) {
                        cameraCallback.onFailed();
                    }
                }
            }
        });

    }

    public void fetchWeatherData(double lng, double lat, WeatherCallback weatherCallback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://weatherapi-com.p.rapidapi.com/forecast.json?q="+lat+"%2C"+lng+"&days=3")
                .get()
                .addHeader("x-rapidapi-key", "f9b146fc48msh9ccc5478784c9e1p167f39jsnbbb987f72424")
                .addHeader("x-rapidapi-host", "weatherapi-com.p.rapidapi.com")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                weatherCallback.onFailed();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    weatherCallback.onFailed();
                } else {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        weatherCallback.onWeatherResponse(new Weather(object.getJSONObject("current")));
                    } catch (JSONException e) {
                        weatherCallback.onFailed();
                    }
                }
            }
        });

    }

    private ArrayList<LiveCamera> parseJSON(String json) throws JSONException {

        ArrayList<LiveCamera> cameras = new ArrayList<>();
        // Parsing..
        JSONArray data = new JSONObject(json).getJSONObject("result")
                .getJSONArray("webcams");
        for(int i = 0; i < data.length(); i++) {
            cameras.add(new LiveCamera(data.getJSONObject(i)));
        }
        return cameras;

    }

}
