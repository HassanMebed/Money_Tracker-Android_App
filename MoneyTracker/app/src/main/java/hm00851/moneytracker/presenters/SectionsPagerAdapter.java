package hm00851.moneytracker.presenters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A FragmentPagerAdapter that returns a fragment corresponding to
 * one of the sections/tabs.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    /** list of all fragments included in the view */
    private final List<Fragment> fragmentList = new ArrayList<>();
    /** list of all of the fragment's titles i.e. tab titles */
    private final List<String> fragmentTitlesList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    /**
     * adds fragments to the adapter which will be displayed in the
     * tab layout.
     * @param fragment
     *          the fragment to be added.
     * @param title
     *          the fragment's title i.e. its tab title.
     */
    public void addFragment(Fragment fragment, String title)
    {
        this.fragmentList.add(fragment);
        this.fragmentTitlesList.add(title);
    }

    //methods generated from the FragmentPagerAdapter class.
    @Override
    public int getCount()
    {
        return this.fragmentList.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        return this.fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return this.fragmentTitlesList.get(position);
    }
}