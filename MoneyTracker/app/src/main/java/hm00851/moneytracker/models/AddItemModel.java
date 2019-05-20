package hm00851.moneytracker.models;

import android.content.Context;
import android.graphics.Bitmap;

import java.text.SimpleDateFormat;

import hm00851.moneytracker.contracts.AddItemContract;

/**
 * Model class for AddItemPresenter
 */
public class AddItemModel extends Model implements AddItemContract.Model
{
    public AddItemModel()
    {
        super();
    }

    /**
     * records current date and formats it then adds inputted item details into the database
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
     * @return true if the operation was successful, false if not.
     */
    @Override
    public boolean addItem(Context context, String name, String details, double value, String category, Bitmap image)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM d yyyy");
        String date = dateFormat.format(new java.util.Date());
        boolean success = DBHandler.getInstance(context).insertIntoItemsTable(name, date, details, value, category, image);

        return success;
    }
}
