package com.social.alexanderpowell.dcmetrotracker.ui.farecalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FareCalculatorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FareCalculatorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}