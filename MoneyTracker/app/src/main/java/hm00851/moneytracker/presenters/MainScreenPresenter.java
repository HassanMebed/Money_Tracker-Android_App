package hm00851.moneytracker.presenters;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import hm00851.moneytracker.contracts.MainScreenContract;
import hm00851.moneytracker.models.DBHandler;
import hm00851.moneytracker.views.TabFragment;

/**
 * Presenter class for MainScreenActivity.
 * fetches data from the database in desired format.
 */
public class MainScreenPresenter implements MainScreenContract.Controller
{
    private MainScreenContract.View view = null;

    public MainScreenPresenter(MainScreenContract.View view)
    {
        this.view = view;
    }

    /**
     * adds sections to a passed SectionsPagerAdapter object, with each section
     * representing a category retrieved from the database.
     * @param context
     *          the activity's context.
     * @param adapter
     *          the adapter to initialize.
     * @return the initialized adapter.
     */
    @Override
    public SectionsPagerAdapter initializeSectionsPagerAdapter(Context context, SectionsPagerAdapter adapter)
    {
        Cursor cursor = DBHandler.getInstance(context).getCategoriesData();

        while(cursor.moveToNext())
        {
            Bundle passedData = new Bundle();

            passedData.putString("tabtitle", cursor.getString(0));

            TabFragment tab = new TabFragment();

            tab.setArguments(passedData);
            adapter.addFragment(tab, cursor.getString(0));
        }

        return adapter;
    }
}