package hm00851.moneytracker.presenters;

import android.content.Context;

import hm00851.moneytracker.contracts.SettingsContract;
import hm00851.moneytracker.models.DBHandler;

/**
 * Presenter class for SettingsActivity.
 * executes database function and displays result..
 */
public class SettingsPresenter implements SettingsContract.Controller
{
    private SettingsContract.View view = null;

    public SettingsPresenter(SettingsContract.View view)
    {
        this.view = view;
    }

    @Override
    public void clearItemsData(Context context)
    {
        DBHandler.getInstance(context).removeAllFromItems();
        this.view.displayToast("All items got deleted.");
    }
}
