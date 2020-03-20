package com.example.geolocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static android.location.LocationManager.GPS_PROVIDER;

public class distCalculator {
    public LocationManager lm;
    private Context mContext;
    public double destinationLat, destinationLon;
    public String destinationAddress;
    public double distance;


    public distCalculator(Context context, String destination) throws IOException {
        this.mContext = context;
        lm = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        Location current = lm.getLastKnownLocation(GPS_PROVIDER);
        double latitude1 = current.getLatitude();
        double longitude1 = current.getLongitude();

        findDestinationCords(destination);
        Location location2 = new Location("dummy provider");
        location2.setLatitude(destinationLat);
        location2.setLongitude(destinationLon);


        float value = location2.distanceTo(current);

        String dist = "" + value;
        distance = Double.valueOf(dist) /1000;

        }

        public void findDestinationCords(String destination) throws IOException {
            Geocoder geocoder = new Geocoder(mContext);
            List<Address> list = geocoder.getFromLocationName(destination, 1);
            Address add = list.get(0);
            destinationLat = add.getLatitude();
            destinationLon = add.getLongitude();
            destinationAddress = add.getAddressLine(0);

        }







    /*
    public double lat1,lat2,lon1,lon2;
    public double distance;
    private Context mContext;
    public String inputDestination;

    public distCalculator(Context context, String inputLocation, String userLocation) throws IOException {
        mContext = context;
        Geocoder geocoder = new Geocoder(context);



        List<Address> list = geocoder.getFromLocationName(inputLocation, 1);
        Address add = list.get(0);
        double latitude2 = add.getLatitude();
        double longitude2 = add.getLongitude();


        String[] cords = userLocation.split(",");
        try
        {
          double latitude1 = Double.parseDouble(cords[0]);
          double longitude1 = Double.parseDouble(cords[1]);
          distance(latitude1,longitude1,latitude2,longitude2);

          List<Address> input = geocoder.getFromLocation(latitude2,longitude2,1);
          Address destAdd = input.get(0);
          int x = destAdd.getMaxAddressLineIndex();
          String destination = destAdd.getAddressLine(x);

          inputDestination = destination;

        }
        catch(Exception e)
        { }



    }

    private void distance(double lat1, double lon1, double lat2, double lon2)
    {

        String unit = "K";
        //String unit must = "K" to calculate kilometres

        if ((lat1 == lat2) && (lon1 == lon2)) {
            distance = 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) *
                    Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) *
                    Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;

                distance = dist;
            }
        }

    }


     */

}
