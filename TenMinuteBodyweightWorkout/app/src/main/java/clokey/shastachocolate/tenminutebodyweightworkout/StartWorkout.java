package clokey.shastachocolate.tenminutebodyweightworkout;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.media.AudioManager;
import android.media.MediaPlayer;



public class StartWorkout extends AppCompatActivity
{

    private TextView timerTextView;
    private long startTime = 0;
    private Button b;
    private boolean workoutChanged = false;
    private int workoutCounter = 0;

    private String[] workoutNames = new String[20];
    private int[] workoutAudioFileResourceNames = new int[20];

    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;



    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            int result = 0;
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if ((seconds == 0 || seconds == 30) && !workoutChanged)
            {
                result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (workoutCounter == workoutNames.length)
                {
                    displayNextWorkout("You Did It!");
                    timerHandler.removeCallbacks(timerRunnable);
                    timerTextView.setText(String.format("%d:%02d", 0, 0));
                    b.setText("Start Workout");
                    return;
                }
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), workoutAudioFileResourceNames[workoutCounter]);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mediaCompletionListener);
                }
                displayNextWorkout(workoutNames[workoutCounter]);
                workoutCounter++;
                workoutChanged = true;
            }
            else
            {
                workoutChanged = false;
            }

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        timerTextView = (TextView) findViewById(R.id.workout_time);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        SetWorkoutNames();
        SetWorkoutAudioFileResources();

        b = (Button) findViewById(R.id.start_workout);
        b.setText("Start Workout");
        b.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (b.getText().equals("Stop Workout"))
                {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("Start Workout");
                    timerTextView.setText(String.format("%d:%02d", 0, 0));
                    displayNextWorkout("Get Ready To Rock!");
                }
                else
                {
                    startTime = System.currentTimeMillis();
                    workoutCounter = 0;
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("Stop Workout");
                }
            }
        });
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    public void displayNextWorkout(String workoutName)
    {
        TextView workoutView = (TextView) findViewById(R.id.workout_name);
        workoutView.setText(workoutName);
    }

    public void SetWorkoutNames()
    {
        workoutNames[0] = "Extended Plank Ups";
        workoutNames[1] = "Shoulder Taps";
        workoutNames[2] = "Plank Supermans";
        workoutNames[3] = "Mountain Climbers";
        workoutNames[4] = "Knee Jumps";
        workoutNames[5] = "Lunge Step Backs";
        workoutNames[6] = "Body-weight Crunch";
        workoutNames[7] = "Scissor Kicks";
        workoutNames[8] = "Candlesticks";
        workoutNames[9] = "Left Side Plank Raise";
        workoutNames[10] = "Right Side Plank Raise";
        workoutNames[11] = "Frog Jump Feet To Hands";
        workoutNames[12] = "Controlled Push-ups";
        workoutNames[13] = "Rest / Stretch";
        workoutNames[14] = "Russian Twists";
        workoutNames[15] = "V-Sit Crunches";
        workoutNames[16] = "Squat To Toe Touch";
        workoutNames[17] = "Squat Jump Ins";
        workoutNames[18] = "Run On The Spot";
        workoutNames[19] = "Plank Knee Ins";
    }

    public void SetWorkoutAudioFileResources()
    {
        workoutAudioFileResourceNames[0] = R.raw.extended_plank_ups;
        workoutAudioFileResourceNames[1] = R.raw.shoulder_taps;
        workoutAudioFileResourceNames[2] = R.raw.plank_supermans;
        workoutAudioFileResourceNames[3] = R.raw.mountain_climbers;
        workoutAudioFileResourceNames[4] = R.raw.knee_jumps;
        workoutAudioFileResourceNames[5] = R.raw.lunge_step_backs;
        workoutAudioFileResourceNames[6] = R.raw.bodyweight_crunch;
        workoutAudioFileResourceNames[7] = R.raw.scissor_kicks;
        workoutAudioFileResourceNames[8] = R.raw.candlesticks;
        workoutAudioFileResourceNames[9] = R.raw.left_side_plank_raise;
        workoutAudioFileResourceNames[10] = R.raw.right_side_plank_raise;
        workoutAudioFileResourceNames[11] = R.raw.frog_jump_feet_to_hands;
        workoutAudioFileResourceNames[12] = R.raw.controlled_pushups;
        workoutAudioFileResourceNames[13] = R.raw.rest_and_stretch;
        workoutAudioFileResourceNames[14] = R.raw.russian_twists;
        workoutAudioFileResourceNames[15] = R.raw.v_sit_crunches;
        workoutAudioFileResourceNames[16] = R.raw.squat_to_toe_touch;
        workoutAudioFileResourceNames[17] = R.raw.squat_jump_ins;
        workoutAudioFileResourceNames[18] = R.raw.run_in_place;
        workoutAudioFileResourceNames[19] = R.raw.plank_knee_ins;
    }


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
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    private void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null)
        {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    MediaPlayer.OnCompletionListener mediaCompletionListener = new MediaPlayer.OnCompletionListener()
    {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer)
        {
            releaseMediaPlayer();
        }
    };
}
