package hm00851.moneytracker.contracts;

import android.content.Context;
import android.graphics.Bitmap;

public interface ViewItemContract
{
    interface View
    {
        void sendAlert(String error);
    }

    interface Controller
    {
        boolean editItemCost(Context context, double newCost, int itemID);
        boolean editItemDetails(Context context, String newDetails, int itemID);
        boolean editItemImage(Context context, Bitmap newImage, int itemID);
        boolean editItemName(Context context, String newName, int itemID);
        boolean removeImage(Context context, int itemID);
        boolean removeItem(Context context, int itemID);
    }
}
