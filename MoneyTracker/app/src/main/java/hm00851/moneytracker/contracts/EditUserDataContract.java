package hm00851.moneytracker.contracts;

import android.content.Context;

public interface EditUserDataContract
{
    interface Model
    {
        boolean editUserData(Context context, String oldName, String oldCurrency, String newName, String newCurrency);
    }

    interface View
    {
        void onValidationError(String error);
    }

    interface Controller
    {
        boolean submitChanges(Context context, String name, String currency);
    }
}
