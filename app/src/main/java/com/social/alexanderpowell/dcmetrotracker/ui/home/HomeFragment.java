package com.social.alexanderpowell.dcmetrotracker.ui.home;

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
import com.social.alexanderpowell.dcmetrotracker.RailStations;
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

public class HomeFragment extends Fragment implements RailColorsRecyclerViewAdapter.ItemClickListener {

    private RailColorsRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        String url = "https://api.wmata.com/Rail.svc/json/jLines";
        RequestQueue mQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
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
        routes.add("BLUE");
        routes.add("GREEN");
        routes.add("ORANGE");
        routes.add("RED");
        routes.add("SILVER");
        routes.add("YELLOW");

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
        //Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), RailStations.class);
        intent.putExtra("COLOR", adapter.getItem(position));
        startActivity(intent);
    }
}