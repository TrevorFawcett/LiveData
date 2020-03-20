package com.example.geolocation;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.core.location.LocationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.*;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class Tracker implements ActivityCompat.OnRequestPermissionsResultCallback{
    private FusedLocationProviderClient fusedLocationClient;
    private final Context mContext;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    public String locationCords;
    LocationData locData;
    public double wayLatitude;
    public double wayLongitude;
    public String test;
    public LocationManager locationManager;


    public Tracker(Context context, LocationData myLocation) {
        this.mContext = context;
        this.locData = myLocation;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        getLocation();

        
    }


    public void requestLocation()
    {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationRequest.setInterval(20 * 1000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();

                    }
                }
            }
        };


    }




    public void getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                /*

                 here to request the missing permissions, and then overriding
                   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                          int[] grantResults)
                 to handle the case where the user grants the permission. See the documentation
                 for Activity#requestPermissions for more details.
                                    Toast.makeText(MainActivity.this, "Permission not granted, Kindly allow permission", Toast.LENGTH_LONG).show();
                */
                showPermissionAlert();
                return;
            }
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.e("LAST LOCATION: ", location.toString());
                            Double latitude = location.getLatitude();
                            Double longitude = location.getLongitude();
                            locationCords = "" + latitude + ", " + longitude;
                            locData.setLocation(locationCords);
                        } else {
                            locData.setLocation("location not found");
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 123: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // permission was denied, show alert to explain permission
                    showPermissionAlert();
                } else {
                    //permission is granted now start a background service
                    if (ActivityCompat.checkSelfPermission(mContext.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(mContext.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getLocation();
                    }
                }
            }

        }
    }

    private void showPermissionAlert() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        }
    }

    public boolean isLocationAvailable()
    {

     return LocationManagerCompat.isLocationEnabled(locationManager);
    }


}
