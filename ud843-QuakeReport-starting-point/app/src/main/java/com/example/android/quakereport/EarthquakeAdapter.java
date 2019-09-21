package com.example.android.quakereport;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Shasta on 2/5/2018.
 */

public class EarthquakeAdapter extends ArrayAdapter
{

    //word adapter constructor which takes the context and list of word translation objects to make a list view out of
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes)
    {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0, earthquakes);
    }

    //overriden get view class used to make a viewgroup to list with more than one subview
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        // Get the {@link WordTranslations} object located at this position in the list
        Earthquake currentEarthquake = (Earthquake) getItem(position);

        // Find the TextView in the list_item.xml layout with the ID magnitude_text_view
        DecimalFormat formatter = new DecimalFormat("0.0");
        String magnitude = formatter.format(Double.parseDouble(currentEarthquake.getMagnitude()));
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);
        magnitudeTextView.setText(magnitude);

        // Find the TextView in the list_item.xml layout with the ID location_text_view
        String rawLocationString = currentEarthquake.getLocation();
        int lenLoc = rawLocationString.length();
        boolean containsOf = rawLocationString.contains("of");
        if (containsOf)
        {
            String proximity = rawLocationString.substring(0, rawLocationString.indexOf("of")+2);
            String location = rawLocationString.substring(rawLocationString.indexOf("of")+2, lenLoc-1);
            TextView proximityTextview = (TextView) listItemView.findViewById(R.id.proximity_text_view);
            proximityTextview.setText(proximity);
            TextView locationTextview = (TextView) listItemView.findViewById(R.id.location_text_view);
            locationTextview.setText(location);
        }
        else
        {
            TextView proximityTextview = (TextView) listItemView.findViewById(R.id.proximity_text_view);
            proximityTextview.setVisibility(View.INVISIBLE);
            TextView locationTextview = (TextView) listItemView.findViewById(R.id.location_text_view);
            locationTextview.setText(currentEarthquake.getLocation());
        }

        // Find the TextView in the list_item.xml layout with the ID date_text_view
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        Date dateObject = new Date(Long.parseLong(currentEarthquake.getDate()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd  , yyyy", Locale.US);
        dateTextView.setText(dateFormat.format(dateObject));

        // Find the TextView in the list_item.xml layout with the ID date_text_view
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);
        timeTextView.setText(timeFormat.format(dateObject));

        // Find the magnitude circle shape and set the color to the correct value
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(Double.parseDouble(currentEarthquake.getMagnitude()));

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    private int getMagnitudeColor(double magnitude)
    {
        int magnitudeColorResId;
        int magFloor = (int)Math.floor(magnitude);

        switch (magFloor)
        {
            case 0:
            case 1:
                magnitudeColorResId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResId);

    }
}
