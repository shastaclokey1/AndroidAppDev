package com.example.android.miwok;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Colors_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Creating the list of english words for the first ten numbers(1-10) I guess we're excluding zero
        ArrayList<WordTranslations> wordsWithTranslations = new ArrayList<>();
        wordsWithTranslations.add(new WordTranslations("Red", "weṭeṭṭi",R.drawable.color_red));
        wordsWithTranslations.add(new WordTranslations("Green", "chokokki",R.drawable.color_green));
        wordsWithTranslations.add(new WordTranslations("Brown", "ṭakaakki",R.drawable.color_brown));
        wordsWithTranslations.add(new WordTranslations("Gray", "ṭopoppi",R.drawable.color_gray));
        wordsWithTranslations.add(new WordTranslations("Black", "kululli",R.drawable.color_black));
        wordsWithTranslations.add(new WordTranslations("White", "kelelli",R.drawable.color_white));
        wordsWithTranslations.add(new WordTranslations("Dusty Yellow", "ṭopiisә",R.drawable.color_dusty_yellow));
        wordsWithTranslations.add(new WordTranslations("Mustard Yellow", "chiwiiṭә",R.drawable.color_mustard_yellow));

        //create a new word adapter that takes in the current activity as the context and the
        //list of english to miwok translations as inputs
        WordAdapter adapter = new WordAdapter(this,wordsWithTranslations, R.color.category_colors);

        //set the list view to the numbers activity parent layout
        ListView listView = (ListView) findViewById(R.id.list);

        //pass our custom adapter to the numbers activity list view
        listView.setAdapter(adapter);
    }

}
