package com.livecamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Splash Screen...
        new Thread() {

            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();

            }

        }.start();

    }

}