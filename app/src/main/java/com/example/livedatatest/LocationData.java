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
        foundLatitude = new MutableLiveData<String>();
    }

    public void setLocation(String location) {
        if(!location.isEmpty())
        {
            String latitude = "" + mTracker.latitude;
            foundLatitude.setValue(latitude);
        }
        else
        {
            foundLatitude.setValue("Failure");
        }
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

    public MutableLiveData<String> getLatitude(Tracker mTracker)
    {
        return foundLatitude;
    }
}
