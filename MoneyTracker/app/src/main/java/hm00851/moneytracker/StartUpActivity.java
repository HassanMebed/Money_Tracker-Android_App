package hm00851.moneytracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import hm00851.moneytracker.models.DBHandler;
import hm00851.moneytracker.models.User;
import hm00851.moneytracker.views.MainScreenActivity;
import hm00851.moneytracker.views.WelcomeActivity;

/**
 * Activity that is called when application first launches;
 * checks to see if user already used application before, or if it's
 * his/her first time.
 */
public class StartUpActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Cursor ownerData = DBHandler.getInstance(this).getOwnerData();

        if(ownerData.getCount() == 0)
        {
            Intent startWelcomeActivity = new Intent(this, WelcomeActivity.class);
            super.startActivity(startWelcomeActivity);
            super.finish();
        }
        else
        {
            User.getInstance().setUserData(this);
            Intent startMainScreenActivity = new Intent(this, MainScreenActivity.class);
            super.startActivity(startMainScreenActivity);
            super.finish();
        }
    }
}

