package hm00851.moneytracker.presenters;

import android.content.Context;

import hm00851.moneytracker.contracts.WelcomeContract;
import hm00851.moneytracker.models.User;
import hm00851.moneytracker.models.WelcomeModel;

/**
 * Presenter class for WelcomeActivity.
 * passes inputted data from activity to model for validation and insertion in
 * the database,
 * updates the UI of the view accordingly.
 */
public class WelcomePresenter implements WelcomeContract.Controller
{
    private WelcomeContract.View view = null;
    private WelcomeModel model = null;

    public WelcomePresenter(WelcomeContract.View view)
    {
        this.view = view;
        this.model = new WelcomeModel();
    }

    @Override
    public boolean onRegister(Context context, String name, String currency)
    {
        boolean success = this.model.register(context, name, currency);

        if(success == true)
        {
            User.getInstance().setUserData(context);
        }
        else
        {
            this.view.failedToRegister(this.model.getModelErrors());
            this.model.clearErrors();
        }

        return success;
    }
}
