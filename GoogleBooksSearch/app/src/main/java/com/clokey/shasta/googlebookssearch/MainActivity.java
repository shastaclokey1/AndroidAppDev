package com.clokey.shasta.googlebookssearch;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>
{
    /** test url to verify that the http network connections are functioning properly */
    private static String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes/query";

    /** Max number of search results from the http request */
    private final int MAX_SEARCH_RESULTS = 10;

    /** Constant value for the earthquake loader ID. We can choose any integer. This really only comes into play if you're using multiple loaders.*/
    private static final int BOOKS_LOADER_ID = 1;

    /** Used to manage loaders and separate threads */
    private ConnectivityManager mConnectivityManager;

    /** Holds information about the network connection(whether there is internet access or not)*/
    private NetworkInfo mNetworkInfo;

    /** Is the loader manager initiallized? Use this to determine when to reset the manager*/
    private boolean isLoadManagerInitialized = false;

    /** Adapter for the list of earthquakes */
    private BooksAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /** Search field for users to enter a book title to look for */
    private EditText mSearchFieldEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new earthquake adapter with the list of earthquakes
        mAdapter = new BooksAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        //set up a listener to detect when one of the list items is clicked and launch the correct activity
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Book currentBook = (Book) mAdapter.getItem(i);
                Intent launchBookPreviewUrl = new Intent(Intent.ACTION_VIEW);
                launchBookPreviewUrl.setData(Uri.parse(currentBook.getPreviewUrl()));
                startActivity(launchBookPreviewUrl);
            }
        });

        //find the empty text view in the main activity and capture it with our variable
        mEmptyStateTextView = findViewById(R.id.empty_view);

        //get a reference to the search text field
        mSearchFieldEditText = findViewById(R.id.search_bar_text);

        //TODO figure out why the http network request doesn't update when I pass it a new url
        //find the search button and set its on click listener to launch the http request
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //set the text from the search field to be the http request query
                REQUEST_URL = buildSearchUrl(mSearchFieldEditText.getText().toString());

//                Uri baseUri = Uri.parse(REQUEST_URL);
//                Uri.Builder uriBuilder = baseUri.buildUpon();
//
//                //telling the http uri the query parameters we want to append
//                uriBuilder.appendQueryParameter("q", mSearchFieldEditText.getText().toString());
//                uriBuilder.appendQueryParameter("maxResults", Integer.toString(MAX_SEARCH_RESULTS));

//                Bundle bundle = new Bundle();
//                bundle.putString("request_url", uriBuilder.toString());

                Log.v("On Search Button Click", REQUEST_URL);

                // Get a reference to the ConnectivityManager to check state of network connectivity
                mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                // Get details on the currently active default data network
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                // If there is a network connection, fetch data
                if (mNetworkInfo != null && mNetworkInfo.isConnected())
                {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getLoaderManager();
                    //if the loader has already been initialized, reset the loader manager to make a new http search
                    if (isLoadManagerInitialized == true)
                        loaderManager.restartLoader(BOOKS_LOADER_ID, null, MainActivity.this);
                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(BOOKS_LOADER_ID, null, MainActivity.this);
                }
                else
                {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    View loadingIndicator = findViewById(R.id.progress_bar);
                    loadingIndicator.setVisibility(View.GONE);

                    //Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle)
    {
        Log.v("onCreateLoader", REQUEST_URL);
        // Create a new loader for the given URL
        return new BooksDataLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books)
    {
        //Turn off the progress bar(hide it)
        View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_books_found);

        Log.v("Title: ", books.get(0).getTitle());
        Log.v("Author: ", books.get(0).getAuthor());
        Log.v("Publisher: ", books.get(0).getPublisher());
        Log.v("Description: ", books.get(0).getDescription());
        Log.v("Preview URL: ", books.get(0).getPreviewUrl());

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty())
            mAdapter.addAll(books);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader)
    {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.v("onLoaderReset", REQUEST_URL);
    }

    private String buildSearchUrl(String searchText)
    {
        //protecting against case where the user just presses enter without typing anything
        if (searchText.equals(""))
        {
            return "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=" + MAX_SEARCH_RESULTS;
        }
        String searchRequestUrl = "https://www.googleapis.com/books/v1/volumes?q=";
        searchRequestUrl += searchText;
        searchRequestUrl += "&maxResults=" + MAX_SEARCH_RESULTS;
        return searchRequestUrl;
    }
}
