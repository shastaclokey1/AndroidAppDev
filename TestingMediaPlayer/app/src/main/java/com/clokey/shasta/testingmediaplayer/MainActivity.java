package com.clokey.shasta.testingmediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.exordium);

        Button play = findViewById(R.id.play_button);

        //Set the click listeners for all of these sweet text views and define the listener so it opens the respective page
        play.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //use the media player to play the specified audio clip
                        mediaPlayer.start();
                    }
                });

        Button pause = findViewById(R.id.pause_button);

        //Set the click listeners for all of these sweet text views and define the listener so it opens the respective page
        pause.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //use the media player to pause the audio audio clip
                        mediaPlayer.pause();
                    }
                });
    }
}
