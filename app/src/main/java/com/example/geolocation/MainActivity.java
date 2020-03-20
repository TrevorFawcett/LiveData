package com.example.geolocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public String userAdd;
    private LocationData myLocation;
    public String trackerLocation;
    public TextView userLoc;
    public boolean locationFound = false;
    public String userLocation;
    public Tracker tracker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLoc = findViewById(R.id.userLoc);


        myLocation = ViewModelProviders.of(this).get(LocationData.class);
        Tracker gpsTracker = new Tracker(this, myLocation);



        LiveData<String> foundLocation = myLocation.getFoundLocation(gpsTracker);

        foundLocation.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                trackerLocation = s;



                if(trackerLocation == "location not found")
                {
                    String douglasCollege = "49.2036, -122.9127";
                    findUserAdd(douglasCollege);


                }
                else{
                    findUserAdd(trackerLocation);
                }

            }
        });



        Button calcbtn = findViewById(R.id.calcBtn);
        calcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputStreet = findViewById(R.id.inputStreet);
                EditText inputCity = findViewById(R.id.inputCity);
                String street = inputStreet.getText().toString();
                String city = inputCity.getText().toString();
                String destLoc = street + ", " + city;


                    try {
                        distCalculator calcD = new distCalculator(MainActivity.this,destLoc);
                        //String inputAddress;
                        double distance = calcD.distance;
                        String roundedDist = String.format("%.2f", distance);
                        String destination = calcD.destinationAddress;
                        String text = "Your location - " + userAdd + "\n" + "Destination - " + destination +
                                "\nDistance = " + roundedDist + " km";
                        userLoc.setText(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



            }
        });
    }

    public void findUserAdd(String location)
    {

            try {
                String[] userCords = location.split(",");
                double userLat = Double.parseDouble(userCords[0]);
                double userLon = Double.parseDouble(userCords[1]);
                Geocoder gc = new Geocoder(this);
                List<Address> list = gc.getFromLocation(userLat,userLon,1);
                Address add = list.get(0);
                userAdd = add.getAddressLine(0);
                userLoc.setText(userAdd);


                //I wrote this in to see if it would give me more accurate cordinates than google
                if(location == "49.2036, -122.9127")
                {
                    double newLat = add.getLatitude();
                    double newLon = add.getLongitude();
                    userLocation = newLat + ", " + newLon;
                }
                else
                    {
                        userLocation = location;
                    }

                locationFound = true;
            } catch (IOException e) {
                e.printStackTrace();
            }



    }


}
