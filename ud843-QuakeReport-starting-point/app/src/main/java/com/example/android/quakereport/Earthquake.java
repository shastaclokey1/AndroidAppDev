package com.example.android.quakereport;

/**
 * Created by Shasta on 2/5/2018.
 */

public class Earthquake
{
    private String magnitude, location, date, url;

    public Earthquake(String magnitude, String location, String date, String url)
    {
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;
        this.url = url;
    }

    public String getMagnitude()
    {
        return magnitude;
    }

    public String getLocation()
    {
        return location;
    }

    public String getDate()
    {
        return date;
    }

    public String getUrl()
    {
        return url;
    }
}
