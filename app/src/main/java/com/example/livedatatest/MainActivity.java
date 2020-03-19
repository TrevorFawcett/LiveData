package com.example.livedatatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LocationData myLocation;
    public String trackerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView geoLoc = findViewById(R.id.geoLocation);

        myLocation = ViewModelProviders.of(this).get(LocationData.class);
        Tracker gpsTracker = new Tracker(this, myLocation);



        LiveData<String> foundLocation = myLocation.getFoundLocation(gpsTracker);

        foundLocation.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                trackerLocation = s;
                TextView tv = findViewById(R.id.textTitle);
                tv.setText(s);
            }
        });


        String userLocation = "49.202987, -122.918138";
                //userLocation should be trackerLocation if it returns a value
        String userInput = "V3M 525";
        //This is the postal code of Douglas College for testing
        try {
            distCalculator calculateD = new distCalculator(this,userLocation,userInput);
            Double calculatedDistance = calculateD.distance; // Distance in kilometers
            geoLoc.setText("Distance = " + calculatedDistance + " KM");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void findAddress()
    {

    }


}