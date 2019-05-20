package hm00851.moneytracker.presenters;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import hm00851.moneytracker.contracts.ManageCategoriesContract;
import hm00851.moneytracker.models.DBHandler;

/**
 * Presenter class for ManageCategoriesActivity.
 * fetches data from the database in desired format.
 */
public class ManageCategoriesPresenter implements ManageCategoriesContract.Controller
{
    private ManageCategoriesContract.View view = null;

    public ManageCategoriesPresenter(ManageCategoriesContract.View view)
    {
        this.view = view;
    }

    /**
     * @param context
     *          the activity's context.
     * @return list of all the user's added category names retrieved from the database.
     */
    @Override
    public ArrayList<String> getCategories(Context context)
    {
        ArrayList<String> categories = new ArrayList<>();
        Cursor cursor = DBHandler.getInstance(context).getCategoriesData();

        while(cursor.moveToNext())
        {
            categories.add(cursor.getString(0));
        }

        return categories;
    }
}
