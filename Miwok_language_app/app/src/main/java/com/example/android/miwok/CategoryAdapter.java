package com.example.android.miwok;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Shasta on 2/2/2018.
 */

public class CategoryAdapter extends FragmentPagerAdapter
{
    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "Numbers", "Colors", "Family Members", "Phrases" };

    public CategoryAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
            return new NumbersFragment();
        else if (position == 1)
            return new ColorsFragment();
        else if (position == 2)
            return new FamilyMembersFragment();
        else
            return new PhrasesFragment();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
