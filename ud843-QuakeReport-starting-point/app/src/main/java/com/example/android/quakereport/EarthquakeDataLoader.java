package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shasta on 2/7/2018.
 */

public class EarthquakeDataLoader extends AsyncTaskLoader<List<Earthquake>>
{
    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeDataLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public EarthquakeDataLoader(Context context, String url)
    {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground()
    {
        //check for null urls and for the case when no urls get passed in
        if (mUrl == null)
            return null;

        //otherwise start the async http request for earthquake data
        ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}