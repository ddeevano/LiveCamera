package com.livecamera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.VideoView;

import com.livecamera.api.DataFetcher;
import com.livecamera.api.LiveCamera;
import com.livecamera.api.Weather;
import com.livecamera.api.interfaces.WeatherCallback;

public class CameraDetails extends AppCompatActivity implements WeatherCallback {

    // Attributes..
    private LiveCamera camera;
    private ProgressDialog progressDialog;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_details);

        init();

    }

    private void init() {

        camera = (LiveCamera) getIntent().getSerializableExtra("camera");

        webView = findViewById(R.id.livecamera_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(camera.getEmbed());

        ((TextView) findViewById(R.id.cd_id)).setText(camera.getId());
        ((TextView) findViewById(R.id.cd_title)).setText(camera.getTitle());
        ((TextView) findViewById(R.id.cd_views)).setText(camera.getViews()+"");
        ((TextView) findViewById(R.id.cd_region)).setText(camera.getLocation().getRegionCode()+" - "
                +camera.getLocation().getRegion());
        ((TextView) findViewById(R.id.cd_city)).setText(camera.getLocation().getCity());
        ((TextView) findViewById(R.id.cd_location)).setText(camera.getLocation().getLongitude()+" , "+
                camera.getLocation().getLatitude());
        ((TextView) findViewById(R.id.cd_timezone)).setText(camera.getLocation().getTimezone());

        progressDialog = new ProgressDialog(CameraDetails.this);
        progressDialog.setMessage("Fetching Weather Details");
        progressDialog.setTitle("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        DataFetcher fetcher = new DataFetcher();
        fetcher.fetchWeatherData(Double.parseDouble(camera.getLocation().getLongitude()),
                Double.parseDouble(camera.getLocation().getLatitude()), this);

    }

    // Updating..
    private void updateWeatherDetails(Weather weather) {

        ((TextView) findViewById(R.id.cd_temperature)).setText(weather.getCentigrade()+"C / "+
                weather.getFahrenheit()+"F");
        ((TextView) findViewById(R.id.cd_wind)).setText(weather.getWindDirection()+" "+
                weather.getWind_mph()+"mph");
        ((TextView) findViewById(R.id.cd_condition)).setText(weather.getCondition());
        ((TextView) findViewById(R.id.cd_humidity)).setText(weather.getHumidity()+"");
        ((TextView) findViewById(R.id.cd_feels)).setText(weather.getFeelsLikeC()+"C / "+
                weather.getFeelsLikeF()+"F");
        ((TextView) findViewById(R.id.cd_visibility)).setText(weather.getVisibility()+" miles");

    }

    @Override
    public void onWeatherResponse(Weather weather) {

        progressDialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateWeatherDetails(weather);
            }
        });

    }

    @Override
    public void onFailed() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setMessage("Unable to Fetch Data, Please connect Internet/WiFi");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}