package com.social.alexanderpowell.dcmetrotracker.ui.railcolors;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.social.alexanderpowell.dcmetrotracker.RailColorsRecyclerViewAdapter;
import com.social.alexanderpowell.dcmetrotracker.R;
import com.social.alexanderpowell.dcmetrotracker.RailStationsActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONObject;

public class RailColorsFragment extends Fragment implements RailColorsRecyclerViewAdapter.ItemClickListener {

    private RailColorsRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String url = "https://api.wmata.com/Rail.svc/json/jLines";
        RequestQueue mQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
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

        mQueue.add(jsonObjectRequest);

        ArrayList<String> routes = new ArrayList<>();
        routes.add("Blue");
        routes.add("Green");
        routes.add("Orange");
        routes.add("Red");
        routes.add("Silver");
        routes.add("Yellow");

        RecyclerView recyclerView = root.findViewById(R.id.train_routes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RailColorsRecyclerViewAdapter(getContext(), routes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        return root;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), RailStationsActivity.class);
        String colorCode = getColorCode(adapter.getItem(position));
        intent.putExtra("COLOR_CODE", colorCode);
        startActivity(intent);
    }

    private String getColorCode(String color) {
        String ret = "";
        color = color.toLowerCase();
        if (color.equals("red")) {
            ret = getString(R.string.red_code);
        } else if (color.equals("yellow")) {
            ret = getString(R.string.yellow_code);
        } else if (color.equals("green")) {
            ret = getString(R.string.green_code);
        } else if (color.equals("blue")) {
            ret = getString(R.string.blue_code);
        } else if (color.equals("orange")) {
            ret = getString(R.string.orange_code);
        } else if (color.equals("silver")) {
            ret = getString(R.string.silver_code);
        }
        return ret;
    }
}