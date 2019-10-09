package clokey.shastachocolate.tenminutebodyweightworkout;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioGroup workoutPicker;

    private Exercise[] workout;
    private Exercise[] nextWorkout;

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
                if (workoutCounter == workout.length)
                {
                    workoutInstructor.speak("You Did It!", TextToSpeech.QUEUE_FLUSH, null, null);
                    displayNextWorkout("You Did It!");
                    workoutPicker.setVisibility(View.VISIBLE);
                    timerHandler.removeCallbacks(timerRunnable);
                    timerTextView.setText("");
                    exerciseDurationTextView.setText("");
                    b.setText("Start Workout");
                    return;
                }
                workoutInstructor.speak(workout[workoutCounter].getName(), TextToSpeech.QUEUE_FLUSH, null, null);
                displayNextWorkout(workout[workoutCounter].getName());
                timeForNextExercise = timeForNextExercise + workout[workoutCounter].getDuration();
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
        workoutPicker = (RadioGroup) findViewById(R.id.workout_type);

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        nextWorkout = InitMackenzieAbWorkout();

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
                    workoutPicker.setVisibility(View.VISIBLE);
                    displayNextWorkout("Get Ready To Rock!");
                }
                else
                {
                    startTime = System.currentTimeMillis();
                    workoutCounter = 0;
                    timeForNextExercise = 0;
                    workout = nextWorkout;
                    workoutPicker.setVisibility(View.INVISIBLE);
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId())
        {
            case R.id.abs:
                if (checked)
                    nextWorkout = InitMackenzieAbWorkout();
                    break;
            case R.id.arms:
                if (checked)
                    nextWorkout = InitArmWorkout();
                    break;
            case R.id.legs:
                if (checked)
                    nextWorkout = InitLegWorkout();
                    break;
            case R.id.full_body:
                if (checked)
                    nextWorkout = InitFullBodyWorkout();
                    break;
        }
    }

    private void displayNextWorkout(String workoutName)
    {
        TextView workoutView = (TextView) findViewById(R.id.workout_name);
        workoutView.setText(workoutName);
    }

    private Exercise[] InitMackenzieAbWorkout()
    {
        Exercise[] workout = new Exercise[8];

        workout[0] = new Exercise("Plank", 120);
        workout[1] = new Exercise("Left Side Plank", 60);
        workout[2] = new Exercise("Right Side Plank", 60);
        workout[3] = new Exercise("High Pushup", 120);
        workout[4] = new Exercise("Plank", 60);
        workout[5] = new Exercise("Left Side Plank", 60);
        workout[6] = new Exercise("Right Side Plank", 60);
        workout[7] = new Exercise("High Pushup", 60);

        return workout;
    }

    private Exercise[] InitArmWorkout()
    {
        Exercise[] workout = new Exercise[10];

        workout[0] = new Exercise("Controlled Push-ups", 30);
        workout[1] = new Exercise("Coffee Table", 60);
        workout[2] = new Exercise("Shoulder Shrug Plank", 120);
        workout[3] = new Exercise("Touch Toes", 30);
        workout[4] = new Exercise("Controlled Push-ups", 30);
        workout[5] = new Exercise("Coffee Table", 60);
        workout[6] = new Exercise("Shoulder Shrug Plank", 120);
        workout[7] = new Exercise("Touch Toes", 30);
        workout[8] = new Exercise("Controlled Push-ups", 60);
        workout[9] = new Exercise("Shoulder Shrug Plank", 60);


        return workout;
    }

    private Exercise[] InitLegWorkout()
    {
        Exercise[] workout = new Exercise[11];

        workout[0] = new Exercise("Body-weight Squats", 60);
        workout[1] = new Exercise("Lunges Left Forward", 60);
        workout[2] = new Exercise("Lunges Left Backward", 60);
        workout[3] = new Exercise("Squat Hops", 30);
        workout[4] = new Exercise("Mountain Climbers", 30);
        workout[5] = new Exercise("Burpies", 30);
        workout[6] = new Exercise("Butt Kickers", 30);
        workout[7] = new Exercise("High Knees", 60);
        workout[8] = new Exercise("Left Leg Calf Raises", 60);
        workout[9] = new Exercise("Right Leg Calf Raises", 60);
        workout[10] = new Exercise("Stretch", 120);

        return workout;
    }

    private Exercise[] InitFullBodyWorkout()
    {
        Exercise[] workout = new Exercise[10];

        workout[0] = new Exercise("Controlled Push-ups", 60);
        workout[1] = new Exercise("Burpies", 60);
        workout[2] = new Exercise("Body-weight Squats", 60);
        workout[3] = new Exercise("Skullers", 60);
        workout[4] = new Exercise("Russian Twists", 60);
        workout[5] = new Exercise("Right Side Plank", 60);
        workout[6] = new Exercise("Shoulder Shrug Plank", 60);
        workout[7] = new Exercise("Left Side Plank", 60);
        workout[8] = new Exercise("Jumping Jacks", 60);
        workout[9] = new Exercise("High Knees", 60);

        return workout;
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
