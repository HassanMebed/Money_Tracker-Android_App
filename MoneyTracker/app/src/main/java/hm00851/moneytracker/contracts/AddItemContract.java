package hm00851.moneytracker.contracts;

import android.content.Context;
import android.graphics.Bitmap;

public interface AddItemContract
{
    interface Model
    {
        boolean addItem(Context context, String name, String details, double value, String category, Bitmap image);
    }

    interface View
    {
        void displayValidationErrorForItemCost(String error);
        void displayValidationErrorForItemName(String error);
        void sendAlert(String error);
    }

    interface Controller
    {
        String[] getCategoriesArray(Context context, String selectedTab);
        boolean submit(Context context, String name, String details, String value, String category, Bitmap image);
    }
}
