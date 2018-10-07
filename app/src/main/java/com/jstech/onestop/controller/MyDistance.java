package com.jstech.onestop.controller;

import android.location.Location;

public class MyDistance {
    double lat1, lat2, long1, long2;
   public MyDistance(double lat1, double long1, double lat2, double long2)
    {
        this.lat1 = lat1;
        this.lat2 = lat2;
        this.long1 = long1;
        this.long2 = long2;
    }

    public float round2(float number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }

    public String getDistance()
    {

        float[] distanceArr = new float[2];

        Location.distanceBetween( lat1, long1,
                lat2, long2, distanceArr);

        float distanceFloat = distanceArr[0]/1000;
        String distance = String.valueOf(round2(distanceFloat, 2));
        return distance;
    }
}