package com.example.android.miwok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Numbers_Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Creating the list of english words for the first ten numbers(1-10) I guess we're excluding zero
        ArrayList<WordTranslations> wordsWithTranslations = new ArrayList<>();
        wordsWithTranslations.add(new WordTranslations("One", "Lutti", R.drawable.number_one));
        wordsWithTranslations.add(new WordTranslations("Two", "Otiiko", R.drawable.number_two));
        wordsWithTranslations.add(new WordTranslations("Three", "Tolookosu", R.drawable.number_three));
        wordsWithTranslations.add(new WordTranslations("Four", "Oyyisa", R.drawable.number_four));
        wordsWithTranslations.add(new WordTranslations("Five", "Massokka", R.drawable.number_five));
        wordsWithTranslations.add(new WordTranslations("Six", "Temmokka", R.drawable.number_six));
        wordsWithTranslations.add(new WordTranslations("Seven", "Kenekaku", R.drawable.number_seven));
        wordsWithTranslations.add(new WordTranslations("Eight", "Kawinta", R.drawable.number_eight));
        wordsWithTranslations.add(new WordTranslations("Nine", "Wo'e", R.drawable.number_nine));
        wordsWithTranslations.add(new WordTranslations("Ten", "Na'aacha", R.drawable.number_ten));

        //create a new word adapter that takes in the current activity as the context and the
        //list of english to miwok translations as inputs
        WordAdapter adapter = new WordAdapter(this,wordsWithTranslations, R.color.category_numbers);

        //set the list view to the numbers activity parent layout
        ListView listView = (ListView) findViewById(R.id.list);

        //pass our custom adapter to the numbers activity list view
        listView.setAdapter(adapter);
    }

}
