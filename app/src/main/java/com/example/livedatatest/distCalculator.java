package com.example.livedatatest;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;

public class distCalculator
{

    public double lat1,lat2,lon1,lon2;
    public double distance;
    private Context mContext;

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
}
