/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        //Find the text views that define the numbers, family members, colors, and phrases pages
        TextView numbers = (TextView) findViewById(R.id.numbers);
        TextView familyMembers = (TextView) findViewById(R.id.family);
        TextView colors = (TextView) findViewById(R.id.colors);
        TextView phrases = (TextView) findViewById(R.id.phrases);

        //Set the click listeners for all of these sweet text views and define the listener so it opens the respective page
        numbers.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //make the numbers activity intent
                        Intent numbersIntent = new Intent(MainActivity.this, Numbers_Activity.class);
                        //start the numbers activity using the intent
                        startActivity(numbersIntent);
                    }
                });

        familyMembers.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //make the family members activity intent
                        Intent familyMembersIntent = new Intent(MainActivity.this, Family_Members_Activity.class);
                        //start the family members activity using the intent
                        startActivity(familyMembersIntent);
                    }
                });

        colors.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //make the colors activity intent
                        Intent colorsIntent = new Intent(MainActivity.this, Colors_Activity.class);
                        //start the colors activity using the intent
                        startActivity(colorsIntent);
                    }
                });

        phrases.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //make the phrases activity intent
                        Intent phrasesIntent = new Intent(MainActivity.this, Phrases_Activity.class);
                        //start the phrases activity using the intent
                        startActivity(phrasesIntent);
                    }
                });
    }


}
