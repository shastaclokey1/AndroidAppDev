package com.clokey.shasta.googlebookssearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shasta on 2/7/2018.
 */

public class BooksDataLoader extends AsyncTaskLoader<List<Book>>
{
    /** Tag for log messages */
    private static final String LOG_TAG = BooksDataLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public BooksDataLoader(Context context, String url)
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
    public ArrayList<Book> loadInBackground()
    {
        //check for null urls and for the case when no urls get passed in
        if (mUrl == null)
            return null;

        //otherwise start the async http request for earthquake data
        ArrayList<Book> books = QueryUtils.fetchBooksData(mUrl);
        return books;
    }
}