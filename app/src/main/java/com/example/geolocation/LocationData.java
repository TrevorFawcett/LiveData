package com.example.geolocation;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class LocationData extends ViewModel {

    private MutableLiveData<String> foundLocation;
    private Tracker mTracker;

    public LocationData(){
        foundLocation = new MutableLiveData<String>();
    }

    public void setLocation(String location) {
        /*
        if(!location.isEmpty())
        {
            String latitude = "" + mTracker.locationCords;
            foundLocation.setValue(latitude);
        }
        else
        {
            foundLocation.setValue("Failure");
        }

         */

        if(!location.isEmpty())
        {
            String x;
            if(mTracker.locationCords == null)
            {
                x = location;
            }
            else
            {
                x = mTracker.locationCords;
            }

            foundLocation.setValue(x);
        }

    }

    public MutableLiveData<String> getFoundLocation(Tracker tracker ) {

        mTracker = tracker;
        /*
        if(foundLocation == null)
        {
            foundLocation = new MutableLiveData<String>();

        }

         */
        getLocation(mTracker);

        return foundLocation;
    }

    public MutableLiveData<String> getLocation(Tracker mTracker)
    {
        return foundLocation;
    }
}