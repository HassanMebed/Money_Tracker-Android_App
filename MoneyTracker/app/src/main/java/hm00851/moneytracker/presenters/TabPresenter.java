package hm00851.moneytracker.presenters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import hm00851.moneytracker.contracts.TabContract;
import hm00851.moneytracker.models.DBHandler;
import hm00851.moneytracker.models.Item;

/**
 * Presenter class for each TabFragment.
 * fetches data from the database in desired format.
 */
public class TabPresenter implements TabContract.Controller
{
    private TabContract.View view = null;

    public TabPresenter(TabContract.View view)
    {
        this.view = view;
    }

    /**
     * @param context
     *          the tab's activity's context.
     * @return created array list of items belonging to the tab's category.
     */
    @Override
    public ArrayList<Item> getItems(Context context)
    {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = DBHandler.getInstance(context).getItemsData();

        while(cursor.moveToNext())
        {
            if(cursor.getString(cursor.getColumnIndex("Category")).equals(this.view.getTabTitle()))
            {
                Bitmap image = null;

                if(cursor.getBlob(cursor.getColumnIndex("Image")) != null)
                {
                    image = BitmapFactory.decodeByteArray(cursor.getBlob(cursor.getColumnIndex("Image")), 0, cursor.getBlob(cursor.getColumnIndex("Image")).length);
                }

                items.add(new Item(cursor.getInt(cursor.getColumnIndex("ID")), cursor.getString(cursor.getColumnIndex("Name")), cursor.getString(cursor.getColumnIndex("Category")), cursor.getDouble(cursor.getColumnIndex("Value")), cursor.getString(cursor.getColumnIndex("Date")), cursor.getString(cursor.getColumnIndex("Details")), image));
            }
        }

        return items;
    }
}
