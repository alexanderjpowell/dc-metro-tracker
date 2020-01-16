package com.social.alexanderpowell.dcmetrotracker.ui.farecalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.social.alexanderpowell.dcmetrotracker.R;

public class FareCalculatorFragment extends Fragment {

    private FareCalculatorViewModel fareCalculatorViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fareCalculatorViewModel =
                ViewModelProviders.of(this).get(FareCalculatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_farecalculator, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        fareCalculatorViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}