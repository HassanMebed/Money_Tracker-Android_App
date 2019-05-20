package hm00851.moneytracker.contracts;

import android.content.Context;

public interface SettingsContract
{
    interface View
    {
        void displayToast(String message);
    }

    interface Controller
    {
        void clearItemsData(Context context);
    }
}
