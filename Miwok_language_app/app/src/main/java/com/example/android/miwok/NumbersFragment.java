package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment
{

    //audio manager to be used for playing recordings of miwok words
    private AudioManager mAudioManager;

    //media player for playing sounds
    private MediaPlayer mMediaPlayer;

    //listener used to detect when the audio focus changes(when another activity/program interrupts ours)
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
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null)
        {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    //setting up the completion listener to detect and report when an audio file finishes
    MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer)
        {
            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //create the audio manager
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Creating the list of english words for the first ten numbers(1-10) I guess we're excluding zero
        final ArrayList<WordTranslations> wordsWithTranslations = new ArrayList<>();
        wordsWithTranslations.add(new WordTranslations("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        wordsWithTranslations.add(new WordTranslations("Two", "Otiiko", R.drawable.number_two, R.raw.number_two));
        wordsWithTranslations.add(new WordTranslations("Three", "Tolookosu", R.drawable.number_three, R.raw.number_three));
        wordsWithTranslations.add(new WordTranslations("Four", "Oyyisa", R.drawable.number_four, R.raw.number_four));
        wordsWithTranslations.add(new WordTranslations("Five", "Massokka", R.drawable.number_five, R.raw.number_five));
        wordsWithTranslations.add(new WordTranslations("Six", "Temmokka", R.drawable.number_six, R.raw.number_six));
        wordsWithTranslations.add(new WordTranslations("Seven", "Kenekaku", R.drawable.number_seven, R.raw.number_seven));
        wordsWithTranslations.add(new WordTranslations("Eight", "Kawinta", R.drawable.number_eight, R.raw.number_eight));
        wordsWithTranslations.add(new WordTranslations("Nine", "Wo'e", R.drawable.number_nine, R.raw.number_nine));
        wordsWithTranslations.add(new WordTranslations("Ten", "Na'aacha", R.drawable.number_ten, R.raw.number_ten));

        //create a new word adapter that takes in the current activity as the context and the
        //list of english to miwok translations as inputs
        WordAdapter adapter = new WordAdapter(getActivity(),wordsWithTranslations, R.color.category_numbers);

        //set the list view to the numbers activity parent layout
        ListView listView = (ListView) rootView.findViewById(R.id.list);

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
                    mMediaPlayer = MediaPlayer.create(getActivity(), wordsWithTranslations.get(i).getAudioResourceId());

                    //start playing the audio file

                    mMediaPlayer.start();

                    //create a call back to release the memory allocated to the audio file when it is done playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop()
    {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    public NumbersFragment()
    {
        // Required empty public constructor
    }

}
