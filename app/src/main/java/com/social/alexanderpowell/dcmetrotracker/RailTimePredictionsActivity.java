package com.social.alexanderpowell.dcmetrotracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RailTimePredictionsActivity extends AppCompatActivity implements RailPredictionsRecyclerViewAdapter.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail_time_predictions);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String stationCode = intent.getStringExtra("STATION_CODE");
        String stationName = intent.getStringExtra("STATION_NAME");
        Toast.makeText(getApplicationContext(), stationCode, Toast.LENGTH_SHORT).show();
        setTitle(stationName);

        //
        String url = "https://api.wmata.com/StationPrediction.svc/json/GetPrediction/" + stationCode;
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<String> predictions = new ArrayList<>();

                            JSONArray array = response.getJSONArray("Trains");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String destinationName = object.getString("DestinationName").trim();
                                String numberOfCars = object.getString("Car").trim();
                                String prediction = object.getString("Min").trim(); // or ARR or BRD
                                if (!prediction.isEmpty()) {
                                    if (prediction.equals("ARR") || prediction.equals("BRD")) {
                                        predictions.add(prediction);
                                    } else {
                                        predictions.add(prediction + " minutes, " + numberOfCars + " cars, to: " + destinationName);
                                    }
                                }
                            }

                            RecyclerView recyclerView = findViewById(R.id.rail_time_prediction);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            RailPredictionsRecyclerViewAdapter adapter = new RailPredictionsRecyclerViewAdapter(getApplicationContext(), predictions);
                            adapter.setClickListener(RailTimePredictionsActivity.this);
                            recyclerView.setAdapter(adapter);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                            recyclerView.addItemDecoration(dividerItemDecoration);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("api_key", getString(R.string.wmata_secret_key));
                return params;
            }
        };

        mQueue.add(jsonObjectRequest);
        //

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    //@Override
    //public void onBackPressed() {
    //}

    //@Override
    //public void onResume() {
    //    super.onResume();
    //    Toast.makeText(getApplicationContext(), "on resume", Toast.LENGTH_SHORT).show();
    //}

    @Override
    public void onItemClick(View view, int position) {

    }
}
