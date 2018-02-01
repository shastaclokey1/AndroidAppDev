package com.example.android.miwok;

/**
 * Created by Shasta on 1/30/2018.
 * class is used to store the default and miwok translations for words in our app
 * the class contains a constructor and two getter methods
 * nothing fancy
 */

public class WordTranslations
{
    private String defaultWord;
    private String miwokWord;
    private int imageResourceId;
    private boolean hasImage;

    //here's the constructor for this bad boy
    public WordTranslations(String defaultWord, String miwokWord)
    {
        this.defaultWord = defaultWord;
        this.miwokWord = miwokWord;
        hasImage = false;
    }

    //here is an alternate constructor with an image resource id
    public WordTranslations(String defaultWord, String miwokWord, int imageResourceId)
    {
        this.defaultWord = defaultWord;
        this.miwokWord = miwokWord;
        this.imageResourceId = imageResourceId;
        hasImage = true;
    }

    //here's the three getters for this sweet class
    public String getDefaultWord()
    {
        return defaultWord;
    }

    public String getMiwokWord()
    {
        return miwokWord;
    }

    public int getImageResourceId()
    {
        return imageResourceId;
    }

    public boolean getHasImage() { return hasImage; }
}
