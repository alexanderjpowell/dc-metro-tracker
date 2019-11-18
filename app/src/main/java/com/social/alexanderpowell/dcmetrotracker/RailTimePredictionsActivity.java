package com.social.alexanderpowell.dcmetrotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class RailTimePredictionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail_time_predictions);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String stationCode = intent.getStringExtra("STATION_CODE");
        Toast.makeText(getApplicationContext(), stationCode, Toast.LENGTH_SHORT).show();
    }
}
