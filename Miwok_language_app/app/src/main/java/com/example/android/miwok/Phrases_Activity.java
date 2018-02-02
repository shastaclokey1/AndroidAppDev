package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Phrases_Activity extends AppCompatActivity
{
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
    {
        //changing what happens when we gain and lose audio focus
        @Override
        public void onAudioFocusChange(int focusChange)
        {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                MainActivity.mMediaPlayer.pause();
                MainActivity.mMediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                MainActivity.mMediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //create the audio manager
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Creating the list of english words for the first ten numbers(1-10) I guess we're excluding zero
        final ArrayList<WordTranslations> wordsWithTranslations = new ArrayList<>();
        wordsWithTranslations.add(new WordTranslations("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        wordsWithTranslations.add(new WordTranslations("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        wordsWithTranslations.add(new WordTranslations("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        wordsWithTranslations.add(new WordTranslations("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        wordsWithTranslations.add(new WordTranslations("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        wordsWithTranslations.add(new WordTranslations("Are you coming?", "әәnәs'aa", R.raw.phrase_are_you_coming));
        wordsWithTranslations.add(new WordTranslations("Yes, I’m coming.?", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        wordsWithTranslations.add(new WordTranslations("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        wordsWithTranslations.add(new WordTranslations("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        wordsWithTranslations.add(new WordTranslations("Come here.", "әnni'nem", R.raw.phrase_come_here));

        //create a new word adapter that takes in the current activity as the context and the
        //list of english to miwok translations as inputs
        WordAdapter adapter = new WordAdapter(this,wordsWithTranslations, R.color.category_phrases);

        //set the list view to the numbers activity parent layout
        ListView listView = (ListView) findViewById(R.id.list);

        //pass our custom adapter to the numbers activity list view
        listView.setAdapter(adapter);

        //make the on click listener to handle what happens when each item in the list is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //clear the media manager so it doesn't get congested
                releaseMediaPlayer();

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    //create media player and load it with the right audio file specified by the word translations list at index i
                    MainActivity.mMediaPlayer = MediaPlayer.create(Phrases_Activity.this, wordsWithTranslations.get(i).getAudioResourceId());

                    //start playing the audio file

                    MainActivity.mMediaPlayer.start();

                    //create a call back to release the memory allocated to the audio file when it is done playing
                    MainActivity.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer)
                        {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
    }

    //overriding the on stop method to release the media player when the app is exited
    @Override
    protected void onStop()
    {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if (MainActivity.mMediaPlayer != null)
        {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            MainActivity.mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            MainActivity.mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
