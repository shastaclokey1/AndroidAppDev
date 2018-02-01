package com.example.android.miwok;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shasta on 1/30/2018.
 */

public class WordAdapter extends ArrayAdapter
{
    private int colorResourceID;

    //word adapter constructor which takes the context and list of word translation objects to make a list view out of
    public WordAdapter(Activity context, ArrayList<WordTranslations> wordTranslations, int colorResourceID)
    {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0, wordTranslations);
        this.colorResourceID = colorResourceID;
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
        WordTranslations currentWordTranslation = (WordTranslations) getItem(position);

        // Find the ImageView in the list_item.xml layout with the ID image_icon
        ImageView iconImageView = (ImageView) listItemView.findViewById(R.id.image_icon);
        if (currentWordTranslation.getHasImage() == true)
        {
            // Get the miwok translation from the current WordTranslations object and
            // set this text on the miwok TextView
            iconImageView.setImageResource(currentWordTranslation.getImageResourceId());
        }
        else
        {
            iconImageView.setVisibility(View.GONE);
        }

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        // Get the miwok translation from the current WordTranslations object and
        // set this text on the miwok TextView
        miwokTextView.setText(currentWordTranslation.getMiwokWord());

        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the default translation from the current WordTranslations object and
        // set this text on the default translation TextView
        defaultTextView.setText(currentWordTranslation.getDefaultWord());

        //find the parent view group and set the background color to the value passed by the activity
        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.category_list_parent);
        linearLayout.setBackgroundResource(colorResourceID);

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
