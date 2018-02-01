package com.example.android.miwok;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Family_Members_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Creating the list of english words for the first ten numbers(1-10) I guess we're excluding zero
        ArrayList<WordTranslations> wordsWithTranslations = new ArrayList<>();
        wordsWithTranslations.add(new WordTranslations("Father", "әpә", R.drawable.family_father));
        wordsWithTranslations.add(new WordTranslations("Mother", "әṭa", R.drawable.family_mother));
        wordsWithTranslations.add(new WordTranslations("Son", "angsi", R.drawable.family_son));
        wordsWithTranslations.add(new WordTranslations("Daughter", "tune", R.drawable.family_daughter));
        wordsWithTranslations.add(new WordTranslations("Older Brother", "taachi", R.drawable.family_older_brother));
        wordsWithTranslations.add(new WordTranslations("Younger Brother", "chalitti", R.drawable.family_younger_brother));
        wordsWithTranslations.add(new WordTranslations("Older Sister", "teṭe", R.drawable.family_older_sister));
        wordsWithTranslations.add(new WordTranslations("Younger Sister", "kolliti", R.drawable.family_younger_sister));
        wordsWithTranslations.add(new WordTranslations("Grandmother", "ama", R.drawable.family_grandmother));
        wordsWithTranslations.add(new WordTranslations("Grandfather", "paapa", R.drawable.family_grandfather));

        //create a new word adapter that takes in the current activity as the context and the
        //list of english to miwok translations as inputs
        WordAdapter adapter = new WordAdapter(this,wordsWithTranslations, R.color.category_family);

        //set the list view to the numbers activity parent layout
        ListView listView = (ListView) findViewById(R.id.list);

        //pass our custom adapter to the numbers activity list view
        listView.setAdapter(adapter);
    }

}
