package com.example.livedatatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LocationData myLocation;
    public String trackerLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Tracker gpsTracker = new Tracker(this);

        myLocation = ViewModelProviders.of(this).get(LocationData.class);

        //trackerLat = String.valueOf(gpsTracker.latitude);
        LiveData<String> foundLatitude = myLocation.getFoundLatitude(gpsTracker);

        String latitude = foundLatitude.getValue();
        if(latitude != null)
        {
            TextView tv = findViewById(R.id.textTitle);
            tv.setText(latitude);
        }


        foundLatitude.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                trackerLat = s;
                TextView tv = findViewById(R.id.textTitle);
                tv.setText(s);
            }
        });




    }
}
