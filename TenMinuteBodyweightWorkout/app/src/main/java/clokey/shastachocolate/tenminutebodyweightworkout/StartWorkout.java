package clokey.shastachocolate.tenminutebodyweightworkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;


public class StartWorkout extends AppCompatActivity
{

    TextView timerTextView;
    long startTime = 0;
    Button b;
    boolean workoutChanged = false;
    int workoutCounter = 0;

    String[] workoutNames = new String[20];


    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            if ((seconds == 0 || seconds == 30) && !workoutChanged)
            {
                if (workoutCounter == workoutNames.length)
                {
                    displayNextWorkout("You Did It!");
                    timerHandler.removeCallbacks(timerRunnable);
                    timerTextView.setText(String.format("%d:%02d", 0, 0));
                    b.setText("Start Workout");
                    return;
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

        SetWorkoutNames();

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
}
