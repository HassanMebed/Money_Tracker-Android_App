package hm00851.moneytracker.presenters;

import android.content.Context;

import hm00851.moneytracker.contracts.EditUserDataContract;
import hm00851.moneytracker.models.EditUserDataModel;
import hm00851.moneytracker.models.User;

/**
 * Presenter class for EditUserDataActivity.
 * Processes passed data from the activity and calls methods to
 * update its UI accordingly.
 */
public class EditUserDataPresenter implements EditUserDataContract.Controller
{
    private EditUserDataContract.View view = null;
    private EditUserDataModel model = null;

    public EditUserDataPresenter(EditUserDataContract.View view)
    {
        this.view = view;
        this.model = new EditUserDataModel();
    }

    /**
     * passes input to model for validation and update in the database.
     * Displays any validation errors from the model in the view and upon
     * successful update, calls User singleton object to re-initialize to
     * obtain new value.
     * @param context
     *          the activity's context.
     * @param name
     *          the new user's name.
     * @param currency
     *          the new user's preferred currency.
     */
    @Override
    public boolean submitChanges(Context context, String name, String currency)
    {
        boolean success = this.model.editUserData(context, User.getInstance().getName(), User.getInstance().getCurrency(), name, currency);

        if(success == true)
        {
            User.getInstance().setUserData(context);
        }
        else
        {
            this.view.onValidationError(this.model.getModelErrors());
            this.model.clearErrors();
        }

        return success;
    }
}
