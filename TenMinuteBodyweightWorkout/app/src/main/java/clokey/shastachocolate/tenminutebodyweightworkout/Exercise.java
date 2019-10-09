package clokey.shastachocolate.tenminutebodyweightworkout;

/**
 * Created by Shasta on 10/8/2019.
 */

public class Exercise
{
    private String name;
    private int duration;

    public Exercise()
    {
        this.name = "Plank";
        this.duration = 60;
    }

    public Exercise(String name, int duration)
    {
        this.name = name;
        this.duration = duration;
    }

    public String getName()
    {
        return this.name;
    }

    public int getDuration()
    {
        return this.duration;
    }
}
