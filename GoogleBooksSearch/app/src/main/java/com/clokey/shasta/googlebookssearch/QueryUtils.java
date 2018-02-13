package com.clokey.shasta.googlebookssearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils
{

    //1)Include the necessary http network connection methods from the Static "Utils" class in did you feel
    //  it into quake report within the "QueryUtils" class.
    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Query the USGS dataset and return an {@link ArrayList <Book>} object to represent a single earthquake.
     */
    public static ArrayList<Book> fetchBooksData(String requestUrl)
    {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try
        {
            Log.v("fetchBooksData", requestUrl);
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Book> books = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl)
    {
        URL url = null;
        try
        {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException e)
        {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null)
            return jsonResponse;

        Log.v("makeHttpRequest", url.toString());

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }
        finally
        {
            if (urlConnection != null)
                urlConnection.disconnect();

            if (inputStream != null)
                inputStream.close();

            Log.v("makehttpRequest", "input stream and url connection closed");

        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {}

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Book> extractFeatureFromJson(String jsonResponse)
    {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Book> books = new ArrayList<>();

        // Try to parse the json responce from the http request we made. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try
        {

            // Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            //Convert SAMPLE_JSON_RESPONSE String into a JSONObject
            JSONObject booksJson = new JSONObject(jsonResponse);

            //Extract “features” JSONArray
            JSONArray listOfBooks = booksJson.getJSONArray("items");

            //set up a json object to store the data from each element in the list of books json array
            JSONObject currentBook;

            //set up the current value variables to be stored in the books arraylist
            String currentTitle, currentAuthor, currentPublisher, currentDescription, currentPreviewUrl, currentThumbnailLink;

            //Loop through each feature in the array
            for (int i = 0; i < listOfBooks.length(); i++)
            {
                //Get earthquake JSONObject at position i and Get “properties” JSONObject
                currentBook = listOfBooks.getJSONObject(i).getJSONObject("volumeInfo");

                //Extract “title” for title, Extract “authors” for for the list of authors,
                // Extract “publisher” for publisher, and extract the description for loading usgs web page
                currentTitle = currentBook.getString("title");
                currentAuthor = currentBook.getJSONArray("authors").getString(0);
                currentPublisher = currentBook.getString("publisher");
                currentDescription = currentBook.getString("description");
                currentPreviewUrl = currentBook.getString("previewLink");
                currentThumbnailLink = currentBook.getJSONObject("imageLinks").getString("thumbnail");




                //Create Book java object from title, author, publisher, and description and add it to the list
                books.add(new Book(currentTitle, currentAuthor, currentPublisher, currentDescription, currentPreviewUrl, currentThumbnailLink));
            }

        }
        catch (JSONException e)
        {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the books JSON results", e);
        }

        // Return the list of earthquakes
        return books;
    }

}