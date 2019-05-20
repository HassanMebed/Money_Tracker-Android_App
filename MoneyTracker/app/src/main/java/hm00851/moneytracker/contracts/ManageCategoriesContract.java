package hm00851.moneytracker.contracts;

import android.content.Context;

import java.util.ArrayList;

public interface ManageCategoriesContract
{

    interface View
    {

    }

    interface Controller
    {
        ArrayList<String> getCategories(Context context);
    }
}
