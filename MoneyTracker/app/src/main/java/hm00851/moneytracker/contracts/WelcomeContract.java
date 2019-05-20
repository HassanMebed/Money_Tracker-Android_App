package hm00851.moneytracker.contracts;

import android.content.Context;

public interface WelcomeContract
{
    interface Model
    {
        boolean register(Context context, String name, String currency);
    }

    interface View
    {
        void failedToRegister(String error);
    }

    interface Controller
    {
        boolean onRegister(Context context, String name, String currency);
    }
}

