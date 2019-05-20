package hm00851.moneytracker.contracts;

import android.content.Context;

public interface AddCategoryContract
{
    interface Model
    {
        boolean addCategory(Context context, String category);
    }

    interface View
    {
        void displayValidationError(String error);
        void displaySuccessMessage();
        void sendAlert(String error);
    }

    interface Controller
    {
        void submit(Context context, String category);
    }
}
