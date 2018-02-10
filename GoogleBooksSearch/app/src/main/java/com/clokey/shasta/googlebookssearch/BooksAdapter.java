package com.clokey.shasta.googlebookssearch;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shasta on 2/5/2018.
 */

public class BooksAdapter extends ArrayAdapter
{

    //books adapter constructor which takes the context and list of book objects to make a list view out of
    public BooksAdapter(Activity context, ArrayList<Book> books)
    {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0, books);
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
        Book currentBook = (Book) getItem(position);

        //find the title, author, and publisher text views, then set the text for all of them
        TextView titleTextView = listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentBook.getTitle());

        TextView authorTextView = listItemView.findViewById(R.id.author_text_view);
        authorTextView.setText(currentBook.getAuthor());

        TextView publisherTextView = listItemView.findViewById(R.id.publisher_text_view);
        publisherTextView.setText(currentBook.getPublisher());

        //TODO figure out why the image is not able to be loaded from the url
        ImageView thumbnailImageView = listItemView.findViewById(R.id.thumbnail_image_view);
        Drawable thumbnail = LoadImageFromWebOperations(currentBook.getThumbnailLink());
        if (thumbnail != null)
            thumbnailImageView.setImageDrawable(thumbnail);


        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    private Drawable LoadImageFromWebOperations(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
