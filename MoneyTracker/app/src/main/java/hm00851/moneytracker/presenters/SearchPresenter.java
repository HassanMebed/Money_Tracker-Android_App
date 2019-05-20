package hm00851.moneytracker.presenters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import hm00851.moneytracker.contracts.SearchContract;
import hm00851.moneytracker.models.DBHandler;
import hm00851.moneytracker.models.Item;

/**
 * Presenter class for SearchActivity.
 * fetches data from the database in desired format.
 */
public class SearchPresenter implements SearchContract.Controller
{
    public SearchPresenter()
    {

    }

    /**
     * @param context
     *          the activity's context.
     * @return list of all items created by retrieving all item's data from the database.
     */
    @Override
    public ArrayList<Item> getAllItems(Context context)
    {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = DBHandler.getInstance(context).getItemsData();

        while(cursor.moveToNext())
        {
            Bitmap image = null;

            if(cursor.getBlob(cursor.getColumnIndex("Image")) != null)
            {
                // convert retrieved byte[] image format to bitmap.
                image = BitmapFactory.decodeByteArray(cursor.getBlob(cursor.getColumnIndex("Image")), 0, cursor.getBlob(cursor.getColumnIndex("Image")).length);
            }

            items.add(new Item(cursor.getInt(cursor.getColumnIndex("ID")), cursor.getString(cursor.getColumnIndex("Name")), cursor.getString(cursor.getColumnIndex("Category")), cursor.getDouble(cursor.getColumnIndex("Value")), cursor.getString(cursor.getColumnIndex("Date")), cursor.getString(cursor.getColumnIndex("Details")), image));
        }

        return items;
    }
}