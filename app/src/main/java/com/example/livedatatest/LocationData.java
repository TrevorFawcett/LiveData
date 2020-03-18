package com.example.livedatatest;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class LocationData extends ViewModel {

    private MutableLiveData<String> foundLatitude;
    private Tracker mTracker;

    public LocationData(){

    }

    public MutableLiveData<String> getFoundLatitude(Tracker tracker ) {

        mTracker = tracker;
        if(foundLatitude == null)
        {
            foundLatitude = new MutableLiveData<String>();

        }
        getLatitude(mTracker);

        return foundLatitude;
    }

    public void getLatitude(Tracker mTracker)
    {
        if(mTracker.latitude != 0)
        {
            String latitude = "" + mTracker.latitude;

            foundLatitude.setValue(latitude);
        }
        else
            {
                foundLatitude.setValue("Failure");
            }
    }
}
