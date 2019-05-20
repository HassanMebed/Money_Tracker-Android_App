package hm00851.moneytracker.models;

import android.content.Context;
import android.database.Cursor;

/**
 * singleton class that stores the user's data and provides it through getters
 */
public class User
{
    /** user's name */
    private String name = null;
    /** user's preferred currency */
    private String currency = null;
    /** the accessible instance of this class */
    private static User userInstance = null;

    private User()
    {

    }

    /**
     * @return user's preferred currency
     */
    public String getCurrency()
    {
        return this.currency;
    }

    /**
     * @return user's name
     */
    public String getName()
    {
        return  this.name;
    }

    /**
     * @return instance of this class
     */
    public static synchronized User getInstance()
    {
        if(userInstance == null)
        {
            userInstance = new User();
        }

        return userInstance;
    }

    /**
     * initializes user's data through fetching it from the database.
     * @param context
     *          the calling activity's context.
     */
    public void setUserData(Context context)
    {
        Cursor userData = DBHandler.getInstance(context).getOwnerData();

        userData.moveToNext();
        this.name = userData.getString(0);
        this.currency = userData.getString(1);
    }
}
