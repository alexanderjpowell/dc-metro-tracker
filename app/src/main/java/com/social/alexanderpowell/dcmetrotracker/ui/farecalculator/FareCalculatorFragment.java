package com.social.alexanderpowell.dcmetrotracker.ui.farecalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.social.alexanderpowell.dcmetrotracker.R;

public class FareCalculatorFragment extends Fragment {

    private String[] COUNTRIES = new String[] { "Belgium", "France", "Italy", "Germany", "Spain", "Greece", "UK", "The United Arab Emirates - Dubai" };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_farecalculator, container, false);

        TextView peakFareTextView = root.findViewById(R.id.peakFareTextView);
        TextView offPeakFareTextView = root.findViewById(R.id.offPeakFareTextView);
        TextView seniorDisabledFareTextView = root.findViewById(R.id.seniorDisabledFareTextView);

        peakFareTextView.setText("$5");
        offPeakFareTextView.setText("$6");
        seniorDisabledFareTextView.setText("$7");

        AutoCompleteTextView fromAutoCompleteTextView = root.findViewById(R.id.fromAutoCompleteTextView);
        AutoCompleteTextView toAutoCompleteTextView = root.findViewById(R.id.toAutoCompleteTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.select_dialog_item, COUNTRIES);

        fromAutoCompleteTextView.setAdapter(adapter);
        fromAutoCompleteTextView.setThreshold(1);

        toAutoCompleteTextView.setAdapter(adapter);
        toAutoCompleteTextView.setThreshold(1);

        ImageView iv = root.findViewById(R.id.infoButton);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionBottomDialogFragment actionBottomDialogFragment = ActionBottomDialogFragment.newInstance();
                actionBottomDialogFragment.show(requireActivity().getSupportFragmentManager(), "TAG");
            }
        });

        return root;
    }

}