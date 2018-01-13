package com.example.android.quakereport;

/**
 * Created by Shasta on 1/12/2018.
 */

public class Earthquake
{
    String mag, city, date;
    public Earthquake()
    {
        mag = "";
        city = "";
        date = "";
    }
    public Earthquake(String mag, String city, String date)
    {
        this.mag = mag;
        this.city = city;
        this.date = date;
    }
    public String getMag()
    {
        return mag;
    }
    public void setMag(String mag)
    {
        this.mag = mag;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
}
