package hm00851.moneytracker.contracts;

import android.content.Context;

import java.util.ArrayList;

import hm00851.moneytracker.models.Item;

public interface SearchContract
{
    interface Controller
    {
        ArrayList<Item> getAllItems(Context context);
    }
}
