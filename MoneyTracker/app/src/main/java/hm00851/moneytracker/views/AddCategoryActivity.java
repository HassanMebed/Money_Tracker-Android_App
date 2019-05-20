package hm00851.moneytracker.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hm00851.moneytracker.R;
import hm00851.moneytracker.contracts.AddCategoryContract;
import hm00851.moneytracker.presenters.AddCategoryPresenter;

/**
 * Activity for displaying user interface for user to add a category to the
 * database.
 */
public class AddCategoryActivity extends AppCompatActivity implements AddCategoryContract.View
{
    private LinearLayout inputField = null;
    private EditText category = null;
    private AddCategoryPresenter presenter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.add_category_screen_layout);
        this.presenter = new AddCategoryPresenter(this);
        this.initializeView();
    }

    /**
     * removes any validation errors that were previously created and added
     * to the view as a result of invalid input.
     */
    private void clearValidationError()
    {
        if(this.inputField.getChildCount() == 2)
        {
            this.inputField.removeViewAt(1);
        }
    }

    /**
     * adds a textview containing a red colored error under the input
     * fields indicating a validation error.
     */
    @Override
    public void displayValidationError(String error)
    {
        this.clearValidationError();

        TextView errorMessage = new TextView(this);

        errorMessage.setText(error);
        errorMessage.setTextColor(Color.parseColor("#b22222"));
        this.inputField.addView(errorMessage);
    }

    /**
     * displays message in the bottom notifying user that category was successfully added
     */
    @Override
    public void displaySuccessMessage()
    {
        this.clearValidationError();
        Toast.makeText(this, "Category successfully added.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Onclick method for "ADD" button; sends input to presenter for processing.
     */
    public void onAdd(View view)
    {
        this.presenter.submit(this, this.category.getText().toString());
    }

    /**
     * reloads this activity's parent activity when back button is pressed
     */
    @Override
    public void onBackPressed()
    {
        Intent reloadParentActivity = new Intent(this, ManageCategoriesActivity.class);
        super.startActivity(reloadParentActivity);
    }

    /**
     * initializes UI elements of the layout
     */
    private void initializeView()
    {
        this.inputField = super.findViewById(R.id.inputField);
        this.category = super.findViewById(R.id.category);
    }

    /**
     * displays a pop-up alert box showing an input error.
     * @param error
     *          the error to be displayed
     */
    @Override
    public void sendAlert(String error)
    {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(error)
                .setNegativeButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}