package com.example.android.miwok;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Numbers_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_);

        //Creating the list of english words for the first ten numbers(1-10) I guess we're excluding zero
        ArrayList<String> englishWords = new ArrayList<String>();
        englishWords.add("One");
        englishWords.add("Two");
        englishWords.add("Three");
        englishWords.add("Four");
        englishWords.add("Five");
        englishWords.add("Six");
        englishWords.add("Seven");
        englishWords.add("Eight");
        englishWords.add("Nine");
        englishWords.add("Ten");

        //checking that the words array was initialized correctly
        //Log.v("Numbers_Activity", "The number at index 5 is " + englishWords.get(5));

        //finding the root view(linear layout) of our number's activity
        LinearLayout rootView = (LinearLayout)findViewById(R.id.numbers_rootView);

        //adding all the english words for numbers to the root view
        for (int i = 0; i < englishWords.size(); i++)
        {
            //create a temporary text view to make the current english number
            TextView currentEnglishNum = new TextView(this );
            //set the text of our current text view
            currentEnglishNum.setText(englishWords.get(i));
            //add the text view to our parent linear layout
            rootView.addView(currentEnglishNum);
        }
    }

}
