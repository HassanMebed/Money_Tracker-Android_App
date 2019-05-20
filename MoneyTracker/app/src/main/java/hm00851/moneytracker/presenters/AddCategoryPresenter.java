package hm00851.moneytracker.presenters;

import android.content.Context;

import hm00851.moneytracker.contracts.AddCategoryContract;
import hm00851.moneytracker.models.AddCategoryModel;

/**
 * Presenter class for AddCategoryActivity.
 * Processes passed data from the activity and calls methods to
 * update its UI accordingly.
 */
public class AddCategoryPresenter implements AddCategoryContract.Controller
{
    private AddCategoryContract.View view = null;
    private AddCategoryModel model = null;

    public AddCategoryPresenter(AddCategoryContract.View view)
    {
        this.view = view;
        this.model = new AddCategoryModel();
    }

    /**
     * validates passed category before sending it to the model
     * to check if there any duplicates of it and to add it if not.
     * @param context
     *          the activity's context.
     * @param category
     *          the new category to be added.
     */
    @Override
    public void submit(Context context, String category)
    {
        if(category.isEmpty())
        {
            this.view.displayValidationError("*Required");
        }
        else
        {
            category.toLowerCase();

            category = category.substring(0, 1).toUpperCase() + category.substring(1);
            boolean success = this.model.addCategory(context, category);

            if(success == true)
            {
                this.view.displaySuccessMessage();
            }
            else
            {
                this.view.sendAlert(this.model.getModelErrors());
            }
        }
    }
}