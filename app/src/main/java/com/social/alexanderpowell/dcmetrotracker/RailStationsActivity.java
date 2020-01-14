package com.social.alexanderpowell.dcmetrotracker;

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

public class RailStationsActivity extends AppCompatActivity implements RailPathsRecyclerViewAdapter.ItemClickListener {

    private RailPathsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rail_stations);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        final String route_color_code = intent.getStringExtra("COLOR_CODE");

        Toast.makeText(getApplicationContext(), route_color_code, Toast.LENGTH_LONG).show();

        //
        String url = "https://api.wmata.com/Rail.svc/json/jStations";
        String LineCode = route_color_code;
        url = url + "?" + "LineCode=" + LineCode;
        //

        //
        //String url = "https://api.wmata.com/Rail.svc/json/jPath";
        //String FromStationCode = "A15";
        //String ToStationCode = "B11";
        //url = url + "?" + "FromStationCode=" + FromStationCode + "&" + "ToStationCode=" + ToStationCode;

        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<String> stationNames = new ArrayList<>();
                            ArrayList<String> stationCodes = new ArrayList<>();
                            //JSONArray array = response.getJSONArray("Path");
                            JSONArray array = response.getJSONArray("Stations");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = (JSONObject)array.get(i);
                                //String stationName = object.getString("StationName");
                                String stationName = object.getString("Name");
                                //String stationCode = object.getString("StationCode");
                                String stationCode = object.getString("Code");
                                Log.d("Routes", stationName);
                                stationNames.add(stationName);
                                stationCodes.add(stationCode);
                            }

                            RecyclerView recyclerView = findViewById(R.id.train_stations);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            adapter = new RailPathsRecyclerViewAdapter(getApplicationContext(), stationNames, stationCodes, route_color_code);
                            adapter.setClickListener(RailStationsActivity.this);
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
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(getApplicationContext(), "You clicked " + adapter.getStationCode(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, RailTimePredictionsActivity.class);
        intent.putExtra("STATION_NAME", adapter.getStationName(position));
        intent.putExtra("STATION_CODE", adapter.getStationCode(position));
        startActivity(intent);
    }
}
