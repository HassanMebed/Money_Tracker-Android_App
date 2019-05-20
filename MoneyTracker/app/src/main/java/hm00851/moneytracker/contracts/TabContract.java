package hm00851.moneytracker.contracts;

import android.content.Context;

import java.util.List;

import hm00851.moneytracker.models.Item;

public interface TabContract
{
    interface View
    {
        String getTabTitle();
    }

    interface Controller
    {
        List<Item> getItems(Context context);
    }
}
