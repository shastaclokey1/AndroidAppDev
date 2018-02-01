package com.example.android.miwok;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class Phrases_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Creating the list of english words for the first ten numbers(1-10) I guess we're excluding zero
        ArrayList<WordTranslations> wordsWithTranslations = new ArrayList<>();
        wordsWithTranslations.add(new WordTranslations("Where are you going?", "minto wuksus"));
        wordsWithTranslations.add(new WordTranslations("What is your name?", "tinnә oyaase'nә"));
        wordsWithTranslations.add(new WordTranslations("My name is...", "oyaaset..."));
        wordsWithTranslations.add(new WordTranslations("How are you feeling?", "michәksәs?"));
        wordsWithTranslations.add(new WordTranslations("I’m feeling good.", "kuchi achit"));
        wordsWithTranslations.add(new WordTranslations("Are you coming?", "әәnәs'aa"));
        wordsWithTranslations.add(new WordTranslations("Yes, I’m coming.?", "hәә’ әәnәm"));
        wordsWithTranslations.add(new WordTranslations("I’m coming.", "әәnәm"));
        wordsWithTranslations.add(new WordTranslations("Let’s go.", "yoowutis"));
        wordsWithTranslations.add(new WordTranslations("Come here.", "әnni'nem"));

        //create a new word adapter that takes in the current activity as the context and the
        //list of english to miwok translations as inputs
        WordAdapter adapter = new WordAdapter(this,wordsWithTranslations, R.color.category_phrases);

        //set the list view to the numbers activity parent layout
        ListView listView = (ListView) findViewById(R.id.list);

        //pass our custom adapter to the numbers activity list view
        listView.setAdapter(adapter);
    }

}
