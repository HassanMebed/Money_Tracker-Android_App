package hm00851.moneytracker.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.SettingsContract;
import hm00851.moneytracker.presenters.SettingsPresenter;

/**
 * Activity for displaying user interface for showing all the settings options.
 */
public class SettingsActivity extends NavigationMenu implements SettingsContract.View
{
    private SettingsPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.getLayoutInflater().inflate(R.layout.settings_screen_layout, super.contentFrame);

        this.presenter = new SettingsPresenter(this);
    }

    /**
     * displays message in the bottom of the screen.
     * @param message
     *          the text of the message.
     */
    @Override
    public void displayToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * onClick method for button representing option to clear all items data from
     * the database; asks user for confirmation first, before sending command to presenter
     * to remove all items data.
     */
    public void onClearItemsData(View view)
    {
        new AlertDialog.Builder(this)
                .setTitle("CONFIRMATION")
                .setMessage("Are you sure you want to delete all of your recorded items?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        presenter.clearItemsData(SettingsActivity.this);
                    }})
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /**
     * onClick method for button representing option of editing user's data;
     * calls activity for editing user's data.
     */
    public void onEditUserData(View view)
    {
        Intent startEditUserDataActivity = new Intent(this, EditUserDataActivity.class);
        super.startActivity(startEditUserDataActivity);
    }
}
