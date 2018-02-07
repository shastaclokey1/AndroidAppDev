/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity
{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=20";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create an earthquake data fetcher asyc task and tell it to go get data from the usgs website at the url we specify
        EarthquakeDataFetcher asyncDataFetcher = new EarthquakeDataFetcher();
        asyncDataFetcher.execute(USGS_REQUEST_URL);
    }

    //2)Define a private async task implementing class within the main activity which will use the QueryUtils
    //  static methods to fetch data form the usgs database
    //private async task class that runs our earthquake data fetching in the background so not to slow down the UI
    private class EarthquakeDataFetcher extends AsyncTask<String, Void, ArrayList<Earthquake>>
    {

        //3)Override the do in background task to call the necessary http networking funcitons
        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls)
        {
            //check for null urls and for the case when no urls get passed in
            if (urls.length < 1 || urls[0] == null)
                return null;

            //otherwise start the async http request for earthquake data
            ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(urls[0]);
            return earthquakes;
        }

        //5)Override the onPostExecute method to call the update UI method
        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link EarthquakeDataFetcher}).
         */
        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes)
        {
            if (earthquakes.size() < 1 || earthquakes == null)
                return;

            updateUi(earthquakes);
        }
    }


    //4)Make a private void "update ui" method that finds the list view for the main activity, creates a custom
    //  adapter to display the list items, and sets the item on click listener for the list view items
    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(ArrayList<Earthquake> earthquakes)
    {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new earthquake adapter with the list of earthquakes
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        //set up a listener to detect when one of the list items is clicked and launch the correct activity
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Earthquake currentEarthquake = (Earthquake) adapter.getItem(i);
                Intent launchEarthquakeUrl = new Intent(Intent.ACTION_VIEW);
                launchEarthquakeUrl.setData(Uri.parse(currentEarthquake.getUrl()));
                startActivity(launchEarthquakeUrl);
            }
        });
    }


}
