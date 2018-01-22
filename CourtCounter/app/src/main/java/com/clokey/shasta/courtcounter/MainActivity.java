package com.clokey.shasta.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    int totalPointsTeamA = 0;
    int totalPointsTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add3Points(View view)
    {
        if (view.getParent().equals(findViewById(R.id.team_a_parent)))
        {
            totalPointsTeamA = totalPointsTeamA + 3;
            displayForTeamA(totalPointsTeamA);
        }
        else
        {
            totalPointsTeamB = totalPointsTeamB + 3;
            displayForTeamB(totalPointsTeamB);
        }
    }

    public void add2Points(View view)
    {
        if (view.getParent().equals(findViewById(R.id.team_a_parent)))
        {
            totalPointsTeamA = totalPointsTeamA + 2;
            displayForTeamA(totalPointsTeamA);
        }
        else
        {
            totalPointsTeamB = totalPointsTeamB + 2;
            displayForTeamB(totalPointsTeamB);
        }
    }

    public void add1Point(View view)
    {
        if (view.getParent().equals(findViewById(R.id.team_a_parent)))
        {
            totalPointsTeamA = totalPointsTeamA + 1;
            displayForTeamA(totalPointsTeamA);
        }
        else
        {
            totalPointsTeamB = totalPointsTeamB + 1;
            displayForTeamB(totalPointsTeamB);
        }
    }

    public void displayForTeamA(int score)
    {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score)
    {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    public void resetScores(View view)
    {
        totalPointsTeamA = 0;
        totalPointsTeamB = 0;
        displayForTeamA(totalPointsTeamA);
        displayForTeamB(totalPointsTeamB);
    }
}
