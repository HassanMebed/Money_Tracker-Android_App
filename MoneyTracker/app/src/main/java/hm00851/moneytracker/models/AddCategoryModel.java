package hm00851.moneytracker.models;

import android.content.Context;
import android.database.Cursor;

import hm00851.moneytracker.contracts.AddCategoryContract;

/**
 * Model class for AddCategoryPresenter
 */
public class AddCategoryModel extends Model implements AddCategoryContract.Model
{
    public AddCategoryModel()
    {
        super();
    }

    /**
     * checks if inputted category already exists, if not, adds it to the database.
     * @param context
     *          the activity's context.
     * @param category
     *          the inputted category.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean addCategory(Context context, String category)
    {
        boolean success = true;
        Cursor categories = DBHandler.getInstance(context).getCategoriesData();

        while(categories.moveToNext())
        {
            if(categories.getString(0).equals(category))
            {
                success = false;
                super.modelErrors = "Entered category already exists.";
                break;
            }
        }

        if(success == true)
        {
            success = DBHandler.getInstance(context).insertIntoCategoriesTable(category);
        }

        return success;
    }
}