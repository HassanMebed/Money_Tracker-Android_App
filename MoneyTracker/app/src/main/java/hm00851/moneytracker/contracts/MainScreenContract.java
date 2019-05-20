package hm00851.moneytracker.contracts;

import android.content.Context;

import hm00851.moneytracker.presenters.SectionsPagerAdapter;

public interface MainScreenContract
{
    interface View
    {

    }

    interface Controller
    {
        SectionsPagerAdapter initializeSectionsPagerAdapter(Context context, SectionsPagerAdapter adapter);
    }
}
