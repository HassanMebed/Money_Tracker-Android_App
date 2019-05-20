package hm00851.moneytracker.models;

import android.content.Context;

import hm00851.moneytracker.contracts.WelcomeContract;

/**
 * Model class for WelcomePresenter.
 */
public class WelcomeModel extends Model implements WelcomeContract.Model
{
    public WelcomeModel()
    {
        super();
    }

    /**
     * validates inputted user data, formats it, before passing it to the database
     * for insertion.
     * @param context
     *          the activity's context.
     * @param name
     *          the user's inputted name.
     * @param currency
     *          the user's inputted preferred currency.
     * @return true if operation was successful, false if not.
     */
    @Override
    public boolean register(Context context, String name, String currency)
    {
        boolean success = true;

        if(name.isEmpty())
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Name cannot be empty" + "\n";
        }
        else if(!name.matches("[a-zA-Z ]+"))
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Name must contain only letters" + "\n";
        }
        else if(name.split("\\s+").length > 2)
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Name can only be two words" + "\n";
        }
        else if(name.split("\\s+").length == 1)
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Please add your surename" + "\n";
        }

        if(currency.isEmpty())
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Currency cannot be empty";
        }
        else if(currency.length() > 3)
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Currency can be of max 3 characters";
        }
        else if(currency.matches("[0-9]+") || currency.matches("[@#!%&*()_=+,.<>:;'?{}|/`~]"))
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Currency must contain only letters" + "\n" + " or special currency characters";
        }
        else if(currency.split("\\s+").length > 1)
        {
            success = false;
            super.modelErrors = super.modelErrors + "*Currency cannot contain spaces";
        }

        if(success == true)
        {
            // split name into first and last
            String[] splitName = name.split("\\s+");
            String firstName = splitName[0];
            String lastName = splitName[1];
            // capitalize currency e.g: USD
            String formattedCurrency = currency.toUpperCase();

            // capitalize first letter of first name and last name.
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

            String formattedName = firstName + " " + lastName;

            success = DBHandler.getInstance(context).insertIntoOwnerTable(formattedName, formattedCurrency);
        }

        return success;
    }
}