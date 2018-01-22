package com.clokey.shasta.cookies;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
{

    boolean monsterIsFull = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the cookie should be eaten.
     */
    public void eatCookie(View view)
    {
        if (!monsterIsFull)
        {
            // TODO: Find a reference to the ImageView in the layout. Change the image.
            ImageView cookieMonster = (ImageView) findViewById(R.id.android_cookie_image_view);
            cookieMonster.setImageDrawable(getDrawable(R.drawable.after_cookie));

            // TODO: Find a reference to the TextView in the layout. Change the text.
            TextView monsterTalk = (TextView) findViewById(R.id.status_text_view);
            monsterTalk.setText("I'm so full");

            // TODO: Find a reference to the Button in the layout. Change the text.
            Button eatPoopButton = (Button) findViewById(R.id.eat_poop_button);
            eatPoopButton.setText("Poop");
            monsterIsFull = true;
        }
        else
        {
            // TODO: Find a reference to the ImageView in the layout. Change the image.
            ImageView cookieMonster = (ImageView) findViewById(R.id.android_cookie_image_view);
            cookieMonster.setImageDrawable(getDrawable(R.drawable.before_cookie));

            // TODO: Find a reference to the TextView in the layout. Change the text.
            TextView monsterTalk = (TextView) findViewById(R.id.status_text_view);
            monsterTalk.setText("I'm so hungry");

            // TODO: Find a reference to the Button in the layout. Change the text.
            Button eatPoopButton = (Button) findViewById(R.id.eat_poop_button);
            eatPoopButton.setText("Eat Cookie");
            monsterIsFull = false;
        }

    }
}