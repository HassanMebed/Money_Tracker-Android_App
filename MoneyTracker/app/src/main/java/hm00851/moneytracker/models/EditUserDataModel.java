package hm00851.moneytracker.models;

import android.content.Context;

import hm00851.moneytracker.contracts.EditUserDataContract;

/**
 * Model class for EditUserDataPresenter.
 */
public class EditUserDataModel extends Model implements EditUserDataContract.Model
{
    public EditUserDataModel()
    {
        super();
    }

    /**
     * validates new inputted user data, formats user inputted name, before passing
     * the inputted data to the database for updating.
     * @param context
     *          the activity's context.
     * @param oldName
     *          the current user's name.
     * @param oldCurrency
     *          the current user's preferred currency.
     * @param newName
     *          the new user's inputted name.
     * @param newCurrency
     *          the new user's inputted preferred currency.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean editUserData(Context context, String oldName, String oldCurrency, String newName, String newCurrency)
    {
        boolean success = true;

        if(newName.isEmpty())
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Name cannot be empty" + "\n";
        }
        else if(!newName.matches("[a-zA-Z ]+"))
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Name must contain only letters" + "\n";
        }
        else if(newName.split("\\s+").length > 2)
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Name can only be two words" + "\n";
        }
        else if(newName.split("\\s+").length == 1)
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Please add your surename" + "\n";
        }

        if(newCurrency.isEmpty())
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Currency cannot be empty";
        }
        else if(newCurrency.length() > 3)
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Currency can be of max 3 characters";
        }
        else if(newCurrency.matches("[0-9]+") || newCurrency.matches("[@#!%&*()_=+,.<>:;'?{}|/`~]"))
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Currency must contain only letters" + "\n" + " or special currency characters";
        }
        else if(newCurrency.split("\\s+").length > 1)
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Currency cannot contain spaces";
        }

        if(success == true)
        {
            // split name into first and last
            String[] splitName = newName.split("\\s+");
            String firstName = splitName[0];
            String lastName = splitName[1];
            // capitalize currency e.g: USD
            String formattedCurrency = newCurrency.toUpperCase();

            // capitalize first letter of first name and last name.
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

            String formattedName = firstName + " " + lastName;

            success = DBHandler.getInstance(context).updateOwnerData(oldName, oldCurrency, formattedName, formattedCurrency);
        }

        return success;
    }
}
