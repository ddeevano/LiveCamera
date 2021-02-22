package com.livecamera;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.livecamera.api.LiveCamera;
import com.livecamera.api.DataFetcher;
import com.livecamera.api.interfaces.LiveCameraCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LiveCameraCallback {

    // Attributes..
    private DataFetcher dataFetcher;
    private ArrayList<LiveCamera> cameras;
    // UI
    private ProgressDialog progressDialog;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Fetching US Live Camera's");
        progressDialog.setTitle("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        listView = findViewById(R.id.livecamera_listviewdata);

        // Listeners..
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, CameraDetails.class);
                intent.putExtra("camera", cameras.get(position));
                startActivity(intent);

            }
        });

        dataFetcher = new DataFetcher();
        dataFetcher.fetchCameraData(this);

    }

    @Override
    public void onLiveCameraResponse(ArrayList<LiveCamera> cameras) {

        this.cameras = cameras;
        progressDialog.dismiss();
        ArrayList<String> names = new ArrayList<>();
        for(LiveCamera camera: cameras){
            names.add(camera.getTitle());
        }
        // Setting adapter..
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                R.layout.layout_camera_listview, R.id.livecamera_listview_title, names);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
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