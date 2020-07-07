package com.social.alexanderpowell.dcmetrotracker.ui.map;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.social.alexanderpowell.dcmetrotracker.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    //private MapViewModel galleryViewModel;
    private GoogleMap mMap;

    /*public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return root;
    }

    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("railRoutes/" + filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /*String url = "https://api.wmata.com/Rail.svc/json/jStations";
        String lineCode = "RD";
        url = url + "?" + "LineCode=" + lineCode;

        RequestQueue mQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PolylineOptions rectOptions = new PolylineOptions();
                            rectOptions.color(Color.RED);
                            //ArrayList<String> stationNames = new ArrayList<>();
                            //ArrayList<String> stationCodes = new ArrayList<>();
                            String out = "";
                            JSONArray array = response.getJSONArray("Stations");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = (JSONObject)array.get(i);
                                Double latitude = object.getDouble("Lat");
                                Double longitude = object.getDouble("Lon");
                                String name = object.getString("Name");
                                //out += latitude + ":" + longitude;
                                //Log.d("Routes", stationName);
                                //stationNames.add(stationName);
                                //stationCodes.add(stationCode);
                                LatLng latLng = new LatLng(latitude, longitude);
                                rectOptions.add(latLng);
                                mMap.addMarker(new MarkerOptions().position(latLng).title(name));
                            }
                            //Toast.makeText(getContext(), out, Toast.LENGTH_SHORT).show();
                            Polyline polyline = mMap.addPolyline(rectOptions);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        mQueue.add(jsonObjectRequest);*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Washington and move the camera
        LatLng washington = new LatLng(38.9072, -77.0369);
        //mMap.addMarker(new MarkerOptions().position(washington).title("Marker in Washington DC"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(washington, 11));

        // Blue
        try {
            PolylineOptions rectOptions = new PolylineOptions();
            rectOptions.color(Color.BLUE);

            JSONArray array = new JSONArray(loadJSONFromAsset("blue.json"));
            JSONObject object;
            //String stationName;
            Double latitude, longitude;
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                //stationName = object.getString("StationName");
                latitude = object.getDouble("Latitude");
                longitude = object.getDouble("Longitude");
                LatLng latLng = new LatLng(latitude, longitude);
                rectOptions.add(latLng);
                //mMap.addMarker(new MarkerOptions().position(latLng).title(stationName));
            }
            Polyline polyline = mMap.addPolyline(rectOptions);
            //Toast.makeText(getContext(), array.get, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Green
        try {
            PolylineOptions rectOptions = new PolylineOptions();
            rectOptions.color(Color.GREEN);

            JSONArray array = new JSONArray(loadJSONFromAsset("green.json"));
            JSONObject object;
            //String stationName;
            Double latitude, longitude;
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                //stationName = object.getString("StationName");
                latitude = object.getDouble("Latitude");
                longitude = object.getDouble("Longitude");
                LatLng latLng = new LatLng(latitude, longitude);
                rectOptions.add(latLng);
                //mMap.addMarker(new MarkerOptions().position(latLng).title(stationName));
            }
            Polyline polyline = mMap.addPolyline(rectOptions);
            //Toast.makeText(getContext(), array.get, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Orange
        try {
            PolylineOptions rectOptions = new PolylineOptions();
            rectOptions.color(Color.rgb(255, 152, 0));

            JSONArray array = new JSONArray(loadJSONFromAsset("orange.json"));
            JSONObject object;
            //String stationName;
            Double latitude, longitude;
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                //stationName = object.getString("StationName");
                latitude = object.getDouble("Latitude");
                longitude = object.getDouble("Longitude");
                LatLng latLng = new LatLng(latitude, longitude);
                rectOptions.add(latLng);
                //mMap.addMarker(new MarkerOptions().position(latLng).title(stationName));
            }
            Polyline polyline = mMap.addPolyline(rectOptions);
            //Toast.makeText(getContext(), array.get, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Red
        try {
            PolylineOptions rectOptions = new PolylineOptions();
            rectOptions.color(Color.RED);

            JSONArray array = new JSONArray(loadJSONFromAsset("red.json"));
            JSONObject object;
            //String stationName;
            Double latitude, longitude;
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                //stationName = object.getString("StationName");
                latitude = object.getDouble("Latitude");
                longitude = object.getDouble("Longitude");
                LatLng latLng = new LatLng(latitude, longitude);
                rectOptions.add(latLng);
                //mMap.addMarker(new MarkerOptions().position(latLng).title(stationName));
            }
            Polyline polyline = mMap.addPolyline(rectOptions);
            //Toast.makeText(getContext(), array.get, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Silver
        try {
            PolylineOptions rectOptions = new PolylineOptions();
            rectOptions.color(Color.GRAY);

            JSONArray array = new JSONArray(loadJSONFromAsset("silver.json"));
            JSONObject object;
            //String stationName;
            Double latitude, longitude;
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                //stationName = object.getString("StationName");
                latitude = object.getDouble("Latitude");
                longitude = object.getDouble("Longitude");
                LatLng latLng = new LatLng(latitude, longitude);
                rectOptions.add(latLng);
                //mMap.addMarker(new MarkerOptions().position(latLng).title(stationName));
            }
            Polyline polyline = mMap.addPolyline(rectOptions);
            //Toast.makeText(getContext(), array.get, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Yellow
        try {
            PolylineOptions rectOptions = new PolylineOptions();
            rectOptions.color(Color.YELLOW);

            JSONArray array = new JSONArray(loadJSONFromAsset("yellow.json"));
            JSONObject object;
            //String stationName;
            Double latitude, longitude;
            for (int i = 0; i < array.length(); i++) {
                object = array.getJSONObject(i);
                //stationName = object.getString("StationName");
                latitude = object.getDouble("Latitude");// + 0.001;
                longitude = object.getDouble("Longitude");
                LatLng latLng = new LatLng(latitude, longitude);
                rectOptions.add(latLng);
                //mMap.addMarker(new MarkerOptions().position(latLng).title(stationName));
            }
            Polyline polyline = mMap.addPolyline(rectOptions);
            //Toast.makeText(getContext(), array.get, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }




    }
}