package clokey.shastachocolate.tenminutebodyweightworkout;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;

import org.w3c.dom.Text;

import java.util.Locale;


public class StartWorkout extends AppCompatActivity
{

    private TextView timerTextView;
    private long startTime = 0;
    private Button b;
    private int workoutCounter = 0;
    private int timeForNextExercise = 0;
    private TextView exerciseDurationTextView;

    private String[] workoutNames = new String[20];
    private int[] workoutAudioFileResourceNames = new int[20];
    private int[] exerciseDurations = new int[20];

    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;

    TextToSpeech workoutInstructor;



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

            if (seconds == timeForNextExercise)
            {
                if (workoutCounter == workoutNames.length)
                {
                    workoutInstructor.speak("You Did It!", TextToSpeech.QUEUE_FLUSH, null, null);
                    displayNextWorkout("You Did It!");
                    timerHandler.removeCallbacks(timerRunnable);
                    timerTextView.setText("");
                    exerciseDurationTextView.setText("");
                    b.setText("Start Workout");
                    return;
                }
                workoutInstructor.speak(workoutNames[workoutCounter], TextToSpeech.QUEUE_FLUSH, null, null);
                displayNextWorkout(workoutNames[workoutCounter]);
                timeForNextExercise = timeForNextExercise + exerciseDurations[workoutCounter];
                workoutCounter++;
            }

            exerciseDurationTextView.setText(String.format("%02d", timeForNextExercise - seconds));
            timerTextView.setText(String.format("%d:%02d", minutes, seconds % 60));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        timerTextView = (TextView) findViewById(R.id.workout_time);
        exerciseDurationTextView = (TextView) findViewById(R.id.exercise_time_left);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        SetWorkoutNames();
        SetWorkoutAudioFileResources();
        setExerciseDurations();

        workoutInstructor = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if (status != TextToSpeech.ERROR)
                {
                    workoutInstructor.setLanguage(Locale.US);
                }
            }
        });

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
                    timerTextView.setText("");
                    exerciseDurationTextView.setText("");
                    displayNextWorkout("Get Ready To Rock!");
                }
                else
                {
                    startTime = System.currentTimeMillis();
                    workoutCounter = 0;
                    timeForNextExercise = 0;
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

    private void displayNextWorkout(String workoutName)
    {
        TextView workoutView = (TextView) findViewById(R.id.workout_name);
        workoutView.setText(workoutName);
    }

    private void SetWorkoutNames()
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

    private void SetWorkoutAudioFileResources()
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

    private void setExerciseDurations()
    {
        exerciseDurations[0] = 30;
        exerciseDurations[1] = 30;
        exerciseDurations[2] = 30;
        exerciseDurations[3] = 30;
        exerciseDurations[4] = 30;
        exerciseDurations[5] = 30;
        exerciseDurations[6] = 30;
        exerciseDurations[7] = 30;
        exerciseDurations[8] = 30;
        exerciseDurations[9] = 30;
        exerciseDurations[10] = 30;
        exerciseDurations[11] = 30;
        exerciseDurations[12] = 30;
        exerciseDurations[13] = 30;
        exerciseDurations[14] = 30;
        exerciseDurations[15] = 30;
        exerciseDurations[16] = 30;
        exerciseDurations[17] = 30;
        exerciseDurations[18] = 30;
        exerciseDurations[19] = 30;

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
