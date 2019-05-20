package hm00851.moneytracker.presenters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import java.util.ArrayList;

import hm00851.moneytracker.contracts.AddItemContract;
import hm00851.moneytracker.models.AddItemModel;
import hm00851.moneytracker.models.DBHandler;

/**
 * Presenter class for AddItemActivity.
 * Processes passed data from the activity, calls methods to
 * update its UI accordingly, and provides it with data fetched from the database
 * in desired format.
 */
public class AddItemPresenter implements AddItemContract.Controller
{
    private AddItemContract.View view = null;
    private AddItemModel model = null;

    public AddItemPresenter(AddItemContract.View view)
    {
        this.view = view;
        this.model = new AddItemModel();
    }

    /**
     * @return array of all the user's added categories.
     * @param context
     *          the activity's context
     * @param selectedTab
     *           the category that will be the first in the array i.e. the first
     *           one to appear in the selection.
     */
    @Override
    public String[] getCategoriesArray(Context context, String selectedTab)
    {
        Cursor cursor = DBHandler.getInstance(context).getCategoriesData();
        ArrayList<String> categoriesArrayList = new ArrayList<>();

        categoriesArrayList.add(selectedTab);

        //loops through all categories and adds them, except for first category which
        //is already added.
        while(cursor.moveToNext())
        {
            if(!cursor.getString(0).equals(selectedTab))
            {
                categoriesArrayList.add(cursor.getString(0));
            }
        }

        String[] categoriesArray = categoriesArrayList.toArray(new String[categoriesArrayList.size()]);

        return categoriesArray;
    }

    /**
     * validates inputted item details before passing them to the model to be
     * added to the database.
     * @param context
     *          the activity's context.
     * @param name
     *          the item's name.
     * @param details
     *          the item's supporting details.
     * @param value
     *          the item's cost.
     * @param category
     *          the item's category.
     * @param image
     *          the item's supporting image.
     */
    @Override
    public boolean submit(Context context, String name, String details, String value, String category, Bitmap image)
    {
        boolean check = true;
        boolean success = true;

        if(name.isEmpty())
        {
            check = false;

            this.view.displayValidationErrorForItemName("*Required");
        }

        if(value.isEmpty())
        {
            check = false;

            this.view.displayValidationErrorForItemCost("*Required");
        }

        if(check == true)
        {
            double parsedValue = 0.0;
            boolean valueParsed = true;

            try
            {
                parsedValue = Double.parseDouble(value);
            }
            catch(Exception e)
            {
                valueParsed = false;
            }

            if(valueParsed == true)
            {
                if(parsedValue < 0.0)
                {
                    this.view.displayValidationErrorForItemCost("*Cannot be less than 0");
                }
                else
                {
                    name.toLowerCase();

                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    success = this.model.addItem(context, name, details, parsedValue, category, image);

                    if(success == false)
                    {
                        this.view.sendAlert("There was an error in submitting the data.");
                    }
                }
            }
            else
            {
                this.view.sendAlert("There was an error in accepting entered item cost.");
            }
        }
        else
        {
            success = false;
        }

        return success;
    }

}